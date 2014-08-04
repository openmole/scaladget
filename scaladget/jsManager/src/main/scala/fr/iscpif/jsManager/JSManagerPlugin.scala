package fr.iscpif.jsmanager

/*
 * Copyright (C) 13/06/14 Mathieu Leclaire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.{FileWriter, File}
import sbt._
import sbt.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import scala.scalajs.tools.classpath.CompleteNCClasspath
import ScalaJSKeys._
import collection.JavaConversions._
import scala.util.{Failure, Success}
import scalax.io._
import Resource._

object JSManagerPlugin extends Plugin with scala.scalajs.sbtplugin.impl.DependencyBuilders {

  implicit def pathToFile(s: String) = new java.io.File(s)

  lazy val toJs = TaskKey[Unit]("toJs")
  lazy val toHtml = TaskKey[Unit]("toHtml")
  lazy val outputPath = SettingKey[String]("Output path")
  lazy val jsCall = SettingKey[String]("js Function to be called ")

  val jsFiles = Seq()

  val cssFiles = Seq()

  override def globalSettings = Seq(
    outputPath := "",
    jsCall := ""
  )

  def jsManagerCommand(optFile: CompleteNCClasspath,
                       target: File,
                       outputPath: String): Unit = scalaJSFiles(optFile) match {
    case Success((p1: File, p2: File)) => jsManagerCommand(target, outputPath, p1, p2)
    case Failure(t: Throwable) => println(t + "\n" + t.getCause.toString)
  }


  def jsManagerCommand(target: File,
                       outputPath: String,
                       jsFile1: File,
                       jsFile2: File): Unit = {
    val targetFile = if (outputPath.isEmpty) target else new File(outputPath)
    targetFile.mkdirs

    val jsDir = new java.io.File(targetFile, "js")
    jsDir.mkdirs
    val cssDir = new java.io.File(targetFile, "css")
    cssDir.mkdirs

    //Copy the scala-js generated files
    Seq(jsFile1, jsFile2).foreach { f =>
      IO.copyFile(f, new java.io.File(jsDir, f.getName))
    }

    //Copy the resources js libraries
    jsFiles.foreach { name => Resource.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(name)).copyDataTo(fromFile(new java.io.File(jsDir, name)))}
    cssFiles.foreach { name => Resource.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(name)).copyDataTo(fromFile(new java.io.File(cssDir, name)))}

  }

  def htmlManagerCommand(optFile: CompleteNCClasspath,
                         target: File,
                         outputPath: String,
                         jsCall: String) {
    val htmlFile = new java.io.File(if (outputPath.isEmpty) target else new File(outputPath), "scaladget-index.html")
    if (htmlFile.exists) htmlFile.delete
    htmlFile.createNewFile

    val out = Resource.fromFile(htmlFile)

    scalaJSFiles(optFile) match {
      case Success((p1: File, p2: File)) =>

        out write "<html>\n"
        out write "  <head>\n"
        out write "    <meta charset=\"utf-8\">\n"
        cssFiles.foreach { name => out write "      <link rel=\"stylesheet\" href=\"css/" + name + "\"/>\n"}
        jsFiles.foreach { name => out write "      <script type=\"text/javascript\" src=\"js/" + name + "\"></script>\n"}
        out write "      <script type=\"text/javascript\" src=\"js/" + p1.getName + "\"></script>\n"
        out write "      <script type=\"text/javascript\" src=\"js/" + p2.getName + "\"></script>\n"
        out write "  </head>\n"
        out write "  <body>\n"
        out write "    <script>" + jsCall + "</script>\n"
        out write "  </body>\n"
        out write "</html>\n"

        jsManagerCommand(target, outputPath, p1, p2)
      case Failure(t: Throwable) => println(t + "\n" + t.getStackTraceString)
    }
  }

  private def all(resources: File, dir: String) = allFiles(new File(resources, dir)).map {
    _.getAbsolutePath
  }

  private def allFiles(dir: File): Seq[File] = {
    if (dir.isDirectory) {
      val these = dir.listFiles.partition(_.isDirectory)
      these._2 ++ these._1.flatMap(allFiles)
    }
    else Seq(dir)
  }

  private def scalaJSFiles(optFile: CompleteNCClasspath) = optFile.ncjsCode.map {
    _.path
  }.headOption match {
    case Some(path: String) =>
      val file = new File(path)
      val name = file.getName
      val dir = file.getParent
      Success((file, new File(dir, name.replace("opt", "fastopt"))))
    case _ => Failure(new Throwable("No js file has been generated"))
  }

  def jsManagerSettings = {
    super.settings ++
      (toJs <<= (fullOptJS in Compile, target, outputPath) map jsManagerCommand) ++
      (toHtml <<= (fullOptJS in Compile, target, outputPath, jsCall) map htmlManagerCommand)
  } ++ scalaJSSettings
}