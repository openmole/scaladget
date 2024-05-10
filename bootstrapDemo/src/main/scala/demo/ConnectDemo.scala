package demo


import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._

object ConnectDemo extends Demo:


  val sc = sourcecode.Text{

    object Css {
      lazy val columnFlex = cls := "columnFlex"
      lazy val centerColumnFlex = cls := "centerColumnFlex"
      lazy val rowFlex = cls := "rowFlex"
      lazy val centerRowFlex = cls := "centerRowFlex"
    }

    type Role = String
    val admin: Role = "Admin"
    val user: Role = "User"



    case class DetailedInfo(role: Role, omVersion: String, storage: Int, memory: Int, cpu: Double, openMOLEMemory: Int)

    def toGB(size: Int): String = s"${(size.toDouble / 1024).round.toString}"

    def textBlock(title: String, text: String) =
      div(Css.centerColumnFlex,
        div(cls := "statusBlock",
          div(title, cls := "info"),
          div(text, cls := "infoContent")
        )
      )

    def badgeBlock(title: String, text: String) =
      div(Css.centerColumnFlex,
        div(cls := "statusBlock",
          div(title, cls := "info"),
          div(text, badge_info, cls := "userBadge")
        )
      )

    def memoryBar(title: String, value: Int, max: Int) =
      val bar1 = ((value.toDouble / max) * 100).toInt
      val bar2 = 100 - bar1
      val memory: String = value.toString
      println("MEM " + memory)
      div(Css.columnFlex, justifyContent.spaceBetween, cls := "statusBlock barBlock",
        div(title, cls := "info"),
        div(cls := "stacked-bar-graph", marginTop := "10px",
          span(width := s"${bar1}%", cls := "bar-1"),
          span(width := s"${bar2}%", cls := "bar-2")
        ),
        span(Css.centerRowFlex,s"${toGB(value)}/${toGB(max)} GB")
      )

    def userInfoBlock(detailedInfo: DetailedInfo) =
      div(Css.rowFlex, justifyContent.center,
        badgeBlock("Role", detailedInfo.role),
        textBlock("OpendMOLE version", detailedInfo.omVersion),
        textBlock("CPU", detailedInfo.cpu.toString),
        textBlock("Memory", detailedInfo.memory.toString),
        textBlock("OpenMOLE memory", detailedInfo.openMOLEMemory.toString),
        memoryBar("Storage", detailedInfo.storage, 15620),
      )

    userInfoBlock(DetailedInfo(user, "15.0-SNAPSHOT", 10240, 2048, 2, 1024))
  }

  val elementDemo = new ElementDemo {
    def title: String = "User"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 6
  }
