package demo


import org.scalajs.dom.Element
import org.scalajs.dom.raw.{Event, HTMLButtonElement}
import scaladget.ace.ace
import scaladget.bootstrapnative.Table._
import scaladget.bootstrapnative.DataTable.DataRow
import scaladget.bootstrapnative.{Table, bsn}
import scaladget.bootstrapnative.bsn._
import scalatags.JsDom.all._
import java.util.concurrent.ThreadLocalRandom

import com.sun.xml.internal.bind.v2.model.core.ID
import scaladget.tools._

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
      addRow(DataRow(Seq("0.1", "158", "3"), subRow = Some(SubRow(subTable.render, expander)))).
      addRow("22", "11", "33").
      addRow("0.006", "bb", "236").
      addRow("21", "zz", "302").
      addRow("151", "a", "33")


    val expander2 = rx.Var(false)
    val trigger2 = button(btn_danger, "Expand", onclick := { () =>
      expander2() = !expander2.now
    })

    val rand = ThreadLocalRandom.current()

    def randomTable = {
      bsn.dataTable
        .addRow(rand.nextInt(0, 1000).toString, rand.nextInt(0, 100).toString, rand.nextInt(0, 50).toString)
        .render
    }

    val subTable2 = Var(bsn.dataTable.
      addRow("222222", "11111", "33333").
      addRow("0.1111", "111158", "3333").
      addRow("222222", "11111", "33333").render
    )

    val aVar = Var(Seq(5, 7, 8))
    val toBeRemoved: Var[Seq[ReactiveRow]] = Var(Seq())

    lazy val rr: ReactiveRow =
      reactiveRow(
        Var(Seq(span("2.1"), span("3.66"), trigger2)), subRow = Some(SubRow(div(Rx {
        subTable2()
      }), expander2))
      )


    val rr2 = reactiveRow(Var(Seq(span("11"), span("11"), span("111"))))

    val divCollapsibleTable = bsn.table.
      addHeaders("Title 1", "Title 2", "Title 3").
      addRow(rr).
      addRow(rr2).
      addRow(reactiveRow(aVar.map { i => Seq(span(i(0).toString), span(i(1).toString), span(i(2).toString)) })).
      addRow(reactiveRow(Var(Seq(span("11"), span("222"), span("333")))))

    val collapsibleExample = div(
      button(btn_danger, "Expand", onclick := { () => expander() = !expander.now }),
      collapsibleTable.render
    ).render


    def updateVar = aVar() = Seq(rand.nextInt(0, 1000), rand.nextInt(0, 100), rand.nextInt(0, 50))

    def updateTable = {
      val rt = randomTable
      println("RT " + rt)
      subTable2() = rt
    }

    def insert = {
      divCollapsibleTable.insertRow(reactiveRow(Var(Seq(span("2001"), span("30066"), span("9.88")))))
    }

    val updateButton = button(btn_primary, "Update", onclick := { () => updateVar })
    val updateSubTableButton = button(btn_primary, marginLeft := 10, "Update sub table", onclick := { () => updateTable })
    val insertRowButton = button(btn_primary, marginLeft := 10, "Inert row", onclick := { () => insert })
    def deleteRRButton = button(btn_danger, marginLeft := 10, "Delete First", onclick := { () =>
      divCollapsibleTable.delete(rr)
    })


    div(
      h3("TABS"),
      theTabs.render,
      h3("COLLAPSIBLE TABLES"),
      collapsibleExample,
      divCollapsibleTable.render,
      updateButton,
      updateSubTableButton,
      insertRowButton,
      deleteRRButton
    ).render


  }

  val elementDemo = new ElementDemo {
    def title: String = "PlayGround"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
