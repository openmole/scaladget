package fr.iscpif.scaladget.stylesheet

/*
 * Copyright (C) 30/03/16 // mathieu.leclaire@openmole.org
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


import scalatags.JsDom
import scalatags.JsDom.{styles => sty}
import org.scalajs.dom
import scalatags.JsDom.all._
import scalatags.generic.StylePair

package object all extends stylesheetbase.BasePackage with bootstrap.BootstrapPackage with bootstrap2.Bootstrap2Package

package stylesheetbase {


  package object stylesheetbase extends BasePackage

  trait BasePackage {


    type ClassAttrPair = scalatags.generic.AttrPair[dom.Element, String]
    type ModifierSeq = Seq[Modifier]
    val emptyMod: ModifierSeq = Seq()


    def pairing(a: String, b: String): ClassAttrPair = `class` := (a.split(" ") ++ b.split(" ")).distinct.mkString(" ")

    implicit def modifierToModifierSeq(p: Modifier): ModifierSeq = Seq(p)

    implicit def stringToModifierSeq(classString: String): ModifierSeq = toClass(classString)

    implicit def classAttrPairToModifierSeq(classAttrPair: ClassAttrPair): ModifierSeq = Seq(classAttrPair)

    implicit class ComposableClassAttrPair[P <: ClassAttrPair](pair: P) {
      def +++(pair2: ClassAttrPair): ClassAttrPair = {
        if (pair.a.name != "class" || pair2.a.name != "class") toClass("ClassError")
        else pairing(pair.v, pair2.v)
      }

      def +++(mod: ModifierSeq): ModifierSeq = mod +++ pair

      def +++(sty: StylePair[dom.Element, _]): ModifierSeq = Seq(pair, sty)
    }

    implicit class ComposableModifierSeq(modifierSeq: ModifierSeq) {
      private def findClassAttrPair(modifierSeq: ModifierSeq) =
        modifierSeq.collect { case a: ClassAttrPair => a }.filter { x =>
          x.a.name == "class"
        }


      def +++(classPair: ClassAttrPair): ModifierSeq = {
        val attrPair = findClassAttrPair(modifierSeq)

        if (attrPair.isEmpty) modifierSeq :+ classPair
        else modifierSeq.filterNot(_ == attrPair.head) :+ pairing(attrPair.head.v, classPair.v)
      }

      def +++(modifierSeq2: ModifierSeq): ModifierSeq = {
        val attrPair = findClassAttrPair(modifierSeq)
        val attrPair2 = findClassAttrPair(modifierSeq2)
        val classPairing =
          if (attrPair.isEmpty && attrPair2.isEmpty) Seq()
          else Seq((attrPair ++ attrPair2).reduce { (a, b) => pairing(a.v, b.v) })

        modifierSeq.filterNot {
          _ == attrPair
        } ++ modifierSeq2.filterNot {
          _ == attrPair2
        } ++ classPairing

      }

      def divCSS(cssClass: String) = div(`class` := cssClass)
    }

    // Convenient implicit conversions
    implicit def condOnModifierSeq3(t: Tuple3[Boolean, ModifierSeq, ModifierSeq]): ModifierSeq = if (t._1) t._2 else t._3

    implicit def condOnModifierSeq2(t: Tuple2[Boolean, ModifierSeq]): ModifierSeq = condOnModifierSeq3(t._1, t._2, emptyMod)


    def toClass(s: String): ClassAttrPair = `class` := s

    // Explicit builders for ModifierSeq (from string or from condition and two ModifierSeq alternatives)
    def ms(s: String): ModifierSeq = Seq(`class` := s)

    def ms(cond: Boolean, ms1: ModifierSeq, ms2: ModifierSeq = emptyMod): ModifierSeq = condOnModifierSeq3((cond, ms1, ms2))

    // CONVINIENT GENERAL ALIASES

    def paddingTop(t: Int): ModifierSeq = Seq(JsDom.styles.paddingTop := s"$t")

    def paddingBottom(t: Int): ModifierSeq = Seq(JsDom.styles.paddingBottom := s"$t")

    def paddingLeft(t: Int): ModifierSeq = Seq(JsDom.styles.paddingLeft := s"$t")

    def paddingRight(t: Int): ModifierSeq = Seq(JsDom.styles.paddingRight := s"$t")

    def marginTop(t: Int): ModifierSeq = Seq(JsDom.styles.marginTop := s"$t")

    def marginBottom(t: Int): ModifierSeq = Seq(JsDom.styles.marginBottom := s"$t")

    def marginLeft(t: Int): ModifierSeq = Seq(JsDom.styles.marginLeft := s"$t")

    def marginRight(t: Int): ModifierSeq = Seq(JsDom.styles.marginRight := s"$t")

    lazy val floatLeft: ModifierSeq = Seq(float := "left")

    lazy val floatRight: ModifierSeq = Seq(float := "right")

    lazy val transparent: ModifierSeq = Seq(opacity := 0)

    lazy val opaque: ModifierSeq = Seq(opacity := 1)

    lazy val pointer: ModifierSeq = cursor := "pointer"
  }

}

package bootstrap {

  import stylesheetbase.stylesheetbase._

  package object bootstrap extends BootstrapPackage

  trait BootstrapPackage {

    type Glyphicon = ClassAttrPair
    type Navbar = ClassAttrPair
    type LabelStyle = ClassAttrPair
    type ButtonStyle = ClassAttrPair

    private def toGlyphicon(s: String) = toClass(s"glyphicon $s")

    private def toLabel(s: String) = toClass(s"label $s")

    private def toButton(s: String) = toClass(s"btn $s")

    //GHYPHICONS
    lazy val glyph_edit: Glyphicon = toGlyphicon("glyphicon-pencil")
    lazy val glyph_trash: Glyphicon = toGlyphicon("glyphicon-trash")
    lazy val glyph_plus: Glyphicon = toGlyphicon("glyphicon-plus")
    lazy val glyph_plus_sign: Glyphicon = toGlyphicon("glyphicon-plus-sign")
    lazy val glyph_minus_sign: Glyphicon = toGlyphicon("glyphicon-minus-sign")
    lazy val glyph_minus: Glyphicon = toGlyphicon("glyphicon-minus")
    lazy val glyph_ok: Glyphicon = toGlyphicon("glyphicon-ok")
    lazy val glyph_question: Glyphicon = toGlyphicon("glyphicon-question-sign")
    lazy val glyph_file: Glyphicon = toGlyphicon("glyphicon-file")
    lazy val glyph_folder_close: Glyphicon = toGlyphicon("glyphicon-folder-close")
    lazy val glyph_home: Glyphicon = toGlyphicon("glyphicon-home")
    lazy val glyph_upload: Glyphicon = toGlyphicon("glyphicon-cloud-upload")
    lazy val glyph_download: Glyphicon = toGlyphicon("glyphicon-cloud-download")
    lazy val glyph_download_alt: Glyphicon = toGlyphicon("glyphicon-download-alt")
    lazy val glyph_settings: Glyphicon = toGlyphicon("glyphicon-cog")
    lazy val glyph_off: Glyphicon = toGlyphicon("glyphicon-off")
    lazy val glyph_flash: Glyphicon = toGlyphicon("glyphicon-flash")
    lazy val glyph_flag: Glyphicon = toGlyphicon("glyphicon-flag")
    lazy val glyph_remove: Glyphicon = toGlyphicon("glyphicon-remove-sign")
    lazy val glyph_road: Glyphicon = toGlyphicon("glyphicon-road")
    lazy val glyph_fire: Glyphicon = toGlyphicon("glyphicon-fire")
    lazy val glyph_list: Glyphicon = toGlyphicon("glyphicon-list")
    lazy val glyph_stats: Glyphicon = toGlyphicon("glyphicon-stats")
    lazy val glyph_refresh: Glyphicon = toGlyphicon("glyphicon-refresh")
    lazy val glyph_lock: Glyphicon = toGlyphicon("glyphicon-lock")
    lazy val glyph_archive: Glyphicon = toGlyphicon("glyphicon-compressed")
    lazy val glyph_market: Glyphicon = toGlyphicon("glyphicon-shopping-cart")
    lazy val glyph_info: Glyphicon = toGlyphicon("glyphicon-info-sign")
    lazy val glyph_plug: Glyphicon = toGlyphicon("icon-plug")
    lazy val glyph_exclamation: Glyphicon = toGlyphicon("glyphicon-exclamation-sign")
    lazy val glyph_comment: Glyphicon = toGlyphicon("glyphicon-comment")
    lazy val glyph_upload_alt: Glyphicon = toGlyphicon("glyphicon-upload")
    lazy val glyph_arrow_right: Glyphicon = toGlyphicon("glyphicon-arrow-right")
    lazy val glyph_arrow_left: Glyphicon = toGlyphicon("glyphicon-arrow-left")
    lazy val glyph_arrow_right_and_left: Glyphicon = toGlyphicon("glyphicon-resize-horizontal")
    lazy val glyph_filter: Glyphicon = toGlyphicon("glyphicon-filter")
    lazy val glyph_copy: Glyphicon = toGlyphicon("glyphicon-copy")
    lazy val glyph_paste: Glyphicon = toGlyphicon("glyphicon-paste")
    lazy val glyph_time: Glyphicon = toGlyphicon("glyphicon-time")
    lazy val glyph_alph_sorting: Glyphicon = toGlyphicon("glyphicon-sort-by-alphabet")
    lazy val glyph_triangle_bottom: Glyphicon = toGlyphicon("glyphicon-triangle-bottom")
    lazy val glyph_triangle_top: Glyphicon = toGlyphicon("glyphicon-triangle-top")

    //NAVBARS
    lazy val nav: Navbar = toClass("nav")
    lazy val navTabs: Navbar = toClass("nav-tabs")
    lazy val nav_default: Navbar = toClass("navbar-default")
    lazy val nav_inverse: Navbar = toClass("navbar-inverse")
    lazy val nav_staticTop: Navbar = toClass("navbar-static-top")
    lazy val nav_pills: Navbar = toClass("nav-pills")
    lazy val navbar: Navbar = toClass("navbar-nav")
    lazy val navbar_form: Navbar = toClass("navbar-form")
    lazy val navbar_right: Navbar = toClass("navbar-right")
    lazy val navbar_left: Navbar = toClass("navbar-left")

    //LABELS
    lazy val label_default: LabelStyle = toLabel("label-default")
    lazy val label_primary: LabelStyle = toLabel("label-primary")
    lazy val label_success: LabelStyle = toLabel("label-success")
    lazy val label_info: LabelStyle = toLabel("label-info")
    lazy val label_warning: LabelStyle = toLabel("label-warning")
    lazy val label_danger: LabelStyle = toLabel("label-danger")
    lazy val black_label: LabelStyle = toLabel("black-label")

    lazy val controlLabel: ClassAttrPair = toClass("control-label")


    //BUTTONS
    lazy val btn: ButtonStyle = toClass("btn")
    lazy val btn_default: ButtonStyle = toButton("btn-default")
    lazy val btn_primary: ButtonStyle = toButton("btn-primary")
    lazy val btn_success: ButtonStyle = toButton("btn-success")
    lazy val btn_info: ButtonStyle = toButton("btn-info")
    lazy val btn_warning: ButtonStyle = toButton("btn-warning")
    lazy val btn_danger: ButtonStyle = toButton("btn-danger")
    lazy val btn_large: ButtonStyle = toButton("btn-lg")
    lazy val btn_medium: ButtonStyle = toButton("btn-md")
    lazy val btn_small: ButtonStyle = toButton("btn-sm")
    lazy val btn_test: ButtonStyle = toButton("myButton")
    lazy val btn_right: ButtonStyle = toButton("pull-right")

    lazy val btnGroup = toClass("btn-group")
    lazy val btnToolbar: ClassAttrPair = toClass("btn-toolbar")


    //MODALS
    lazy val modal: ClassAttrPair = toClass("modal")
    lazy val fade: ClassAttrPair = toClass("fade")
    lazy val modalDialog: ClassAttrPair = toClass("modal-dialog")
    lazy val modalContent: ClassAttrPair = toClass("modal-content")
    lazy val modalHeader: ClassAttrPair = toClass("modal-header")
    lazy val modalInfo: ClassAttrPair = toClass("modal-info")
    lazy val modalBody: ClassAttrPair = toClass("modal-body")
    lazy val modalFooter: ClassAttrPair = toClass("modal-footer")

    //GRIDS
    def colMD(nbCol: Int): ClassAttrPair = toClass(s"col-md-$nbCol")

    def colMDOffset(offsetSize: Int): ClassAttrPair = toClass(s"col-md-offset-$offsetSize")

    lazy val row: ClassAttrPair = toClass("row")


    //PANELS
    lazy val panel: ClassAttrPair = toClass("panel")
    lazy val panelDefault: ClassAttrPair = toClass("panel-default")
    lazy val panelHeading: ClassAttrPair = toClass("panel-heading")
    lazy val panelBody: ClassAttrPair = toClass("panel-body")


    //TABLES
    lazy val table: ClassAttrPair = toClass("table")
    lazy val bordered: ClassAttrPair = toClass("table-bordered")
    lazy val striped: ClassAttrPair = toClass("table-striped")
    lazy val active: ClassAttrPair = toClass("active")
    lazy val success: ClassAttrPair = toClass("success")
    lazy val danger: ClassAttrPair = toClass("danger")
    lazy val warning: ClassAttrPair = toClass("warning")
    lazy val info: ClassAttrPair = toClass("info")


    //INPUTS
    lazy val inputGroup: ClassAttrPair = toClass("input-group")
    lazy val inputGroupButton: ClassAttrPair = toClass("input-group-btn")
    lazy val inputGroupAddon: ClassAttrPair = toClass("input-group-addon")

    //FORMS
    lazy val formControl: ClassAttrPair = toClass("form-control")
    lazy val formGroup: ClassAttrPair = toClass("form-group")
    lazy val formInline: ClassAttrPair = toClass("form-inline")
    lazy val formHorizontal: ClassAttrPair = toClass("form-horizontal")


    //OTHERS
    lazy val dropdown: ClassAttrPair = toClass("dropdown")
    lazy val dropdownMenu: ClassAttrPair = toClass("dropdown-menu")
    lazy val dropdownToggle: ClassAttrPair = toClass("dropdown-toggle")
    lazy val progress: ClassAttrPair = toClass("progress")
    lazy val progressBar: ClassAttrPair = toClass("progress-bar")
    lazy val container: ClassAttrPair = toClass("container")
    lazy val jumbotron: ClassAttrPair = toClass("jumbotron")
    lazy val themeShowcase: ClassAttrPair = toClass("theme-showcase")
    lazy val controlGroup: ClassAttrPair = toClass("control-group")
    lazy val controls: ClassAttrPair = toClass("controls")
  }

}

package bootstrap2 {

  import stylesheetbase.stylesheetbase._

  package object bootstrap2 extends Bootstrap2Package

  trait Bootstrap2Package {

    //Exclusive Button Group
    def stringInGroup: ModifierSeq = Seq(
      sty.height := "30px",
      sty.paddingTop := "3px",
      sty.paddingLeft := "6px",
      sty.paddingRight := "6px"
    )
    
    def twoGlyphButton: ModifierSeq = Seq(
      sty.top := "1px",
      sty.height := "30px"
    )

    def stringButton: ModifierSeq = Seq(
      sty.top := "4px",
      sty.height := "30px"
    )
  }

}

