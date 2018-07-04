package demo


import org.scalajs.dom.Element
import org.scalajs.dom.raw.{Event, HTMLButtonElement, HTMLDivElement, HTMLElement}
import scaladget.ace.ace
import scaladget.bootstrapnative.Table._
import scaladget.bootstrapnative.DataTable.DataRow
import scaladget.bootstrapnative.{Table, bsn}
import scaladget.bootstrapnative.bsn._
import scalatags.JsDom.all._
import java.util.concurrent.ThreadLocalRandom

import scaladget.tools._
import scalatags.JsDom.TypedTag

import scala.scalajs.js.timers
import scala.scalajs.js.timers.SetTimeoutHandle

object PlayGroundDemo {
  val sc = sourcecode.Text {
    import rx._

    val table = bsn.dataTable.
      addHeaders("Title 1", "Title 2", "Title 3").
      addRow("0.1", "158", "3").
      addRow("0.006", "bb", "236").
      addRow("21", "zz", "302").
      addRow("151", "a", "33")

    val table2 = bsn.dataTable.
      addHeaders("Title 10", "Title 20", "Title 30").
      addRow("0.1", "158", "3").
      addRow("0.006", "bb", "236").
      addRow("21", "zz", "302").
      addRow("151", "a", "33")

    val filteredTable = table.style(bordered_table).sortable

    lazy val filterInput = inputTag("")(marginBottom := 20).render
    filterInput.oninput = (e: Event) ⇒ {
      filteredTable.filter(filterInput.value)
    }

    val tableRender = filteredTable.render
    val tableRender2 = table2.render


    //Editor 1
    val editorDiv = div(id := "editor", height := 100, paddingRight := 20).render
    val editor = ace.edit(editorDiv)
    val session = editor.getSession()

    session.setValue("val a = 7")
    session.setMode("ace/mode/scala")
    editor.setTheme("ace/theme/github")

    val editorRender = div(editorDiv)


    //Editor 2
    val editorDiv2 = div(id := "editor", height := 100, paddingRight := 20).render
    val editor2 = ace.edit(editorDiv2)
    val session2 = editor2.getSession()

    session2.setValue("val a = 777")
    session2.setMode("ace/mode/scala")
    editor2.setTheme("ace/theme/github")

    val editorRender2 = div(editorDiv2)


    lazy val tabs2: Tabs = Tabs.tabs().
      add("Raw", editorRender).
      add("Table", tableRender).build

    val switch: Var[Boolean] = Var(false)


    lazy val switchButton: HTMLButtonElement = button("Switch", btn_default, onclick := { () =>
      val currentTitle = theTabs.active.now.map {
        _.title
      }.getOrElse("NEW")


      val index = theTabs.active.now.map { i => theTabs.tabs.now.indexOf(i) }

      theTabs.active.now.map { t =>
        theTabs.remove(t)
      }

      val newTab = Tab(currentTitle,
        div(
          switchButton,
          if (switch.now) tableRender else editorRender
        ),
        () => {})

      index.map { i =>
        val tabsnow = theTabs.tabs.now
        theTabs.tabs() = tabsnow.take(i) ++ Seq(newTab) ++ tabsnow.takeRight(tabsnow.size - i)
      }

      switch() = !switch.now
      theTabs.setActive(newTab)
    }).render


    lazy val theTabs = Tabs.tabs().closable.
      add("Table", tableRender2).
      add("Div", div("yo")).
      add("Etitor", editorRender2).
      add("Switch",
        div(
          switchButton,
          tableRender
        )).build

    // Collapsible tables
    val expander = rx.Var(true)

    val subTable = bsn.dataTable.
      addRow("222222", "11111", "33333").
      addRow("0.1111", "111158", "3333").
      addRow("222222", "11111", "33333")


    val collapsibleTable = bsn.dataTable.
      addHeaders("Title 1", "Title 2", "Title 3").
      addRow(DataRow(Seq("0.1", "158", "3"))).
      addRow("22", "11", "33").
      addRow("0.006", "bb", "236").
      addRow("21", "zz", "302").
      addRow("151", "a", "33")


    val expander2: Var[Seq[ID]] = Var(Seq())

    //  val expander2: Var[Boolean] = Var(false)

    val triggerCache: Var[Map[ID, TypedTag[HTMLButtonElement]]] = Var(Map())

    def trigger2(id: ID) = {
      button(btn_default, s"Expand $id", onclick := { () =>
        expander2() = {
          expander2.now.find {
            _ == id
          } match {
            case Some(i) => expander2.now.filterNot {
              _ == i
            }
            case _ => expander2.now :+ id
          }
        }
      })
    }

    val rand = ThreadLocalRandom.current()

    def randomTable = {
      bsn.dataTable
        .addRow(rand.nextInt(0, 1000).toString, rand.nextInt(0, 100).toString, rand.nextInt(0, 50).toString)
        .render
    }

    val inTable: Var[Map[ID, MyData]] = Var(Map())
    val plusMinus = Var(1)
    val subTable2: Seq[(ID, Rx[TypedTag[HTMLElement]])] = (0 to 4).map{i=>
      (i.toString, Rx{
        if(plusMinus() > 0)
        div("Tata", backgroundColor := "orange")/*div(randomTable.render)*/
        else div("Toto", backgroundColor := "pink", height := 250)
      })}


    def fakeDIV(id: String) = div(backgroundColor := "pink", height := 400)(s"my fake $id")

    val aVar = Var(Seq(5, 7, 8))
    case class MyData(a: String, b: String, c: String)

    def refresh: SetTimeoutHandle = {
      timers.setTimeout(3000) {
        val next = rand.nextInt(0, 4).toString
        update(next)
        refresh
      }
    }

    def refreshSub: SetTimeoutHandle = {
      timers.setTimeout(5000) {
        val next = rand.nextInt(0, 4).toString
        updateTable
        refreshSub
      }
    }

    def updateTable = {
      plusMinus() = plusMinus.now * -1
    }


    def update(id: ID) = {
      val data = MyData(rand.nextInt(0, 1000).toString, rand.nextInt(0, 100).toString, rand.nextInt(0, 50).toString)
      inTable() = inTable.now.updated(id, data)
    }


    def deleteRRButton(id: ID) = button(btn_danger, marginLeft := 10, "Delete", onclick := { () =>
      inTable() = inTable.now.filterNot(_._1 == id)
    })

    val divCollapsibleTable =
      Table(inTable.map { t =>
        t.map {
          case (id, md) =>
            ReactiveRow(id, Seq(VarCell(span(md.a), 0), VarCell(span(md.b), 1), VarCell(span(md.c), 2), VarCell(trigger2(id), 3), FixedCell(deleteRRButton(id), 4)))
        }.toSeq
      },
        subRow = Some((i:ID)=>{
        //  println("SUBTAB "  + i +" // " + subTable2.now.filter(_._1 == i).head._2.now)
          SubRow(subTable2.filter(_._1 == i).head._2, expander2.map {
            _.contains(i)
          })
        })
    ).addHeaders("Title 1", "Title 2", "Title 3", "")

    val collapsibleExample = div(
      button(btn_danger, "Expand", onclick := { () => expander() = !expander.now }),
      collapsibleTable.render
    ).render

    refresh
    refreshSub

    div(
      h3("TABS"),
      theTabs.render,
      h3("COLLAPSIBLE TABLES"),
      collapsibleExample,
      divCollapsibleTable.render
    ).render


  }

  val elementDemo = new ElementDemo {
    def title: String = "PlayGround"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
