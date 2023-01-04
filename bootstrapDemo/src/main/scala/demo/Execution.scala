package demo

/*
 * Copyright (C) 05/01/17 // mathieu.leclaire@openmole.org
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

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import org.scalajs.dom.HTMLDivElement
import scaladget.bootstrapnative.Table.{BSTableStyle, BasicRow}
import scaladget.tools.Utils._

object ExecutionDemo extends Demo {
  val sc = sourcecode.Text {

    val rowFlex = Seq(display.flex, flexDirection.row, alignItems.center)
    val columnFlex = Seq(display.flex, flexDirection.column, justifyContent.flexStart)

    sealed abstract class Status(val name: String)
    object Running extends Status("Running")
    object Completed extends Status("Completed")
    object Failed extends Status("Failed")
    object Preparing extends Status("Preparing")
    object Canceled extends Status("Canceled")
    object Compiling extends Status("Compiling")

    sealed trait Expand
    object Console extends Expand
    object Script extends Expand
    object ErrorLog extends Expand

    case class Simulation(id: Int, script: String, status: Status)
    val simulations = Seq(
      Simulation(1, "explore.oms", Preparing),
      Simulation(2, "model.oms", Failed),
      Simulation(14, "pse.oms", Running),
      Simulation(17, "ose.oms", Completed)
    )

    val currentOpenSimulation: Var[Option[Simulation]] = Var(None)
    val durationOnCores = Var(false)
    val showExpander: Var[Option[Expand]] = Var(None)
    val script = div("val a = Val[Double]\n\nval b = Val[Int]", cls := "script")
    val console = div("[INFO] Running script ...", cls := "console")
    val errorLog = div("Error while interpreting imports: import org.openmole.core.dsl._; import org.openmole.core.workflow.builder.DefinitionScope.user._; " +
      "import org.openmole.core.keyword._; " +
      "import org.openmole.plugin.domain.bounds._; import org.openmole.plugin.domain.collection._; import ")

    def contextBlock(info: String, content: String) = {
      div(columnFlex, div(cls := "contextBlock", div(info, cls := "info"), div(content, cls := "infoContent")))
    }

    def statusBlock(info: String, content: String) = {
      statusBlockFromDiv(info, div(content, cls := "infoContent"), "statusBlock")
    }

    def statusBlockFromDiv(info: String, contentDiv: Div, blockCls: String) = {
      div(columnFlex, div(cls := blockCls, div(info, cls := "info"), contentDiv))
    }

    def scriptBlock(scriptName: String) =
      div(columnFlex, div(cls := "contextBlock",
        cls <-- showExpander.signal.map { exp =>
          if (exp == Some(Script)) "statusOpen"
          else ""
        },
        div("Script", cls := "info"),
        div(scriptName, cls := "infoContentLink")),
        onClick --> { _ =>
          showExpander.update(exp =>
            if (exp == Some(Script)) None
            else Some(Script)
          )
        },
        cursor.pointer
      )

    val simpleDurationStub = "21:01:17"
    val durationOnCoresStub = "12d 5h 21min"

    val durationBlock =
      div(columnFlex, div(cls := "statusBlock",
        div(child <-- durationOnCores.signal.map { d => if (d) "Duration on cores" else "Duration" }, cls := "info"),
        div(child <-- durationOnCores.signal.map { d => if (d) durationOnCoresStub else simpleDurationStub }, cls := "infoContentLink")),
        onClick --> { _ => durationOnCores.update(!_) },
        cursor.pointer
      )

    val consoleBlock =
      div(columnFlex, div(cls := "statusBlock",
        cls.toggle("", "statusOpen") <-- showExpander.signal.map {
          _ == Some(Console)
        },
        div("Standard output", cls := "info"),
        div(child <-- showExpander.signal.map { c => if (c == Some(Console)) "Hide" else "Show" }, cls := "infoContentLink")),
        onClick --> { _ =>
          showExpander.update(exp =>
            if (exp == Some(Console)) None
            else Some(Console)
          )
        },
        cursor.pointer
      )

    def simulationStatusBlock(simulation: Simulation) =
      div(columnFlex, div(cls := "statusBlockNoColor",
        cls.toggle("", "statusOpen") <-- showExpander.signal.map {
          _ == Some(ErrorLog)
        },
        div("Status", cls := "info"),
        div(simulation.status.name, cls := {
          if (simulation.status == Failed) "infoContentLink"
          else "infoContent"
        }),
        onClick --> { _ =>
          showExpander.update(exp =>
            if (exp == Some(ErrorLog)) None
            else Some(ErrorLog)
          )
        },
        cursor.pointer
      )
      )

    def statusColor(status: Status) = status match {
      case Completed => "#00810a"
      case Failed => "#c8102e"
      case Canceled => "#d14905"
      case Preparing | Compiling => "#f1c306"
      case Running => "#a5be21"
    }

    def simulationBlock(s: Simulation) =
      div(rowFlex, justifyContent.center, alignItems.center,
        cls := "simulationInfo",
        cls.toggle("statusOpenSim") <-- currentOpenSimulation.signal.map { os => os.map(_.id) == Some(s.id) },
        div("", cls := "simulationID", backgroundColor := statusColor(s.status)),
        div(s.script.replace(".oms", "")),
        cursor.pointer,
        onClick --> { _ =>
          currentOpenSimulation.update {
            _ match {
              case None => Some(s)
              case Some(x: Simulation) if (x != s) => Some(s)
              case _ => None
            }
          }
        }
      )

    // oms file, starting time,  algo type (when information is available)
    // // Execution status, nb jobs running, nb job completed, execution duration (effective / parallel), execution detail (errors, execution on remotes), play, stop, trash
    def executionRow(simulation: Simulation) = div(rowFlex,
      scriptBlock(simulation.script),
      contextBlock("Start time", "02/01/2023, 10:41:38"),
      contextBlock("Method", "Profile"),
      durationBlock,
      statusBlock("Running", "245"),
      statusBlock("Completed", "14251"),
      simulationStatusBlock(simulation).amend(backgroundColor := statusColor(simulation.status)),
      consoleBlock
    )


    val omrView = div(rowFlex, height := "300")

    val expander = div(height := "150",
      child <-- showExpander.signal.map {
        _ match {
          case Some(Script) => script
          case Some(Console) => console
          case Some(ErrorLog) => textArea(errorLog)
          case None => div()
        }
      }
    )

    def buildExecution(s: Simulation) = {
      elementTable()
        .addRow(executionRow(s)).expandTo(expander, showExpander.signal.map(_.isDefined))
        .unshowSelection
        .render.render.amend(idAttr := "exec")
    }

    //val executionTables = executions.map{buildExecution(_)}
    //val summaries = executions.map{executionContextSummary(_)}

    div(columnFlex, width := "100%",
      div(rowFlex, justifyContent.center, simulations.map { s =>
        simulationBlock(s)
      }),
      div(child <-- currentOpenSimulation.signal.map {
        _ match {
          case Some(n) => div(buildExecution(n))
          case _ => div()
        }
      }
      )
    )

  }

  val elementDemo = new ElementDemo {
    def title: String = "Exec"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 6
  }
}
