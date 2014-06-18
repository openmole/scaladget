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
import ScalaJSKeys._
import collection.JavaConversions._
import scalax.io._
import Resource._

object JSManagerPlugin extends Plugin {

  implicit def pathToFile(s: String) = new java.io.File(s)

  lazy val toJs = TaskKey[Unit]("toJs")
  lazy val toHtml = TaskKey[Unit]("toHtml")
  lazy val outputPath = SettingKey[String]("Output path")
  lazy val jsCall = SettingKey[String]("js Function to be called ")

  val jsFiles = Seq("d3.v3.min.js",
    "jquery-2.1.1.min.js",
    "bootstrap.min.js")

  val cssFiles = Seq("bootstrap.min.css")

  override def globalSettings = Seq(
    outputPath := "",
    jsCall := ""
  )


  def jsManagerCommand(optFile: File,
                       target: File,
                       resources: File,
                       outputPath: String) = {
    val targetFile = if (outputPath.isEmpty) target else new File(outputPath)

    val jsDir = new java.io.File(targetFile, "js")
    jsDir.mkdirs
    val cssDir = new java.io.File(targetFile, "css")
    cssDir.mkdirs

    //Copy the scala-js generated files
    val optFilename = optFile.getName
    val preoptFileName = optFilename.replace("opt", "preopt")
    IO.copyFile(optFile, new java.io.File(jsDir, optFilename))
    IO.copyFile(new java.io.File(optFile.getParentFile, preoptFileName), new java.io.File(jsDir, preoptFileName))

    //Copy the resources js libraries
    jsFiles.foreach { name => Resource.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(name)).copyDataTo(fromFile(new java.io.File(jsDir, name)))}
    cssFiles.foreach { name => Resource.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(name)).copyDataTo(fromFile(new java.io.File(cssDir, name)))}

  }

  def htmlManagerCommand(optFile: File,
                         target: File,
                         resources: File,
                         outputPath: String,
                         jsCall: String) {
    val htmlFile = new java.io.File(if (outputPath.isEmpty) target else new File(outputPath), "scaladget-index.html")
    if (htmlFile.exists) htmlFile.delete
    htmlFile.createNewFile

    val out = Resource.fromFile(htmlFile)
    val optFilename = optFile.getName
    val preoptFileName = optFilename.replace("opt", "preopt")

    out write "<html>\n"
    out write "  <head>\n"
    out write "    <meta charset=\"utf-8\">\n"
    cssFiles.foreach { name => out write "      <link rel=\"stylesheet\" href=\"css/" + name +"\"/>\n"}
    jsFiles.foreach { name => out write "      <script type=\"text/javascript\" src=\"js/" + name +"\"></script>\n"}
    out write "      <script type=\"text/javascript\" src=\"js/" + optFilename +"\"></script>\n"
    out write "      <script type=\"text/javascript\" src=\"js/" + preoptFileName +"\"></script>\n"
    out write "  </head>\n"
    out write "  <body>\n"
    out write "    <script>" + jsCall + "</script>\n"
    out write "  </body>\n"
    out write "</html>\n"

    jsManagerCommand(optFile, target, resources, outputPath)
  }

  def jsManagerSettings = {
    super.settings ++
      (toJs <<= (optimizeJS in Compile, target, resourceManaged, outputPath) map jsManagerCommand) ++
      (toHtml <<= (optimizeJS in Compile, target, resourceManaged, outputPath, jsCall) map htmlManagerCommand)
  } ++ scalaJSSettings
}