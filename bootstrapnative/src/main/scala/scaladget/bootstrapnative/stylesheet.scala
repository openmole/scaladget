package scaladget.bootstrapnative

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


import scalatags.JsDom.{styles => sty}
import scalatags.JsDom.all._
import scaladget.tools._

package stylesheet {

  //package object bootstrap extends BootstrapPackage

  trait BootstrapPackage {

    type Glyphicon = ModifierSeq
    type Navbar = ModifierSeq
    type LabelStyle = ModifierSeq
    type ButtonStyle = ModifierSeq
    type AlertStyle = ModifierSeq
    type NavStyle = ModifierSeq
    type TableStyle = ModifierSeq

    private def toGlyphicon(s: String) = toClass(s"glyphicon $s")

    private def toLabel(s: String) = toClass(s"label $s")

    private def toButton(s: String) = toClass(s"btn $s")

    private def toAlert(s: String) = toClass(s"alert $s")

    private def toNav(s: String) = toClass(s"nav $s")

    private def toTable(s: String) = toClass(s"table $s")

    //GHYPHICONS
    lazy val glyph_edit: Glyphicon = toGlyphicon("glyphicon-pencil")
    lazy val glyph_edit2: Glyphicon = toGlyphicon("glyphicon-edit")
    lazy val glyph_save: Glyphicon = toGlyphicon("glyphicon-save")
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
    lazy val glyph_repeat: Glyphicon = toGlyphicon("glyphicon-repeat")
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
    lazy val glyph_sort_by_attributes: Glyphicon = toGlyphicon("glyphicon-sort-by-attributes")
    lazy val glyph_sort_by_attributes_alt: Glyphicon = toGlyphicon("glyphicon-sort-by-attributes-alt")
    lazy val glyph_triangle_bottom: Glyphicon = toGlyphicon("glyphicon-triangle-bottom")
    lazy val glyph_triangle_top: Glyphicon = toGlyphicon("glyphicon-triangle-top")
    lazy val glyph_chevron_left: Glyphicon = toGlyphicon("glyphicon glyphicon-chevron-left")
    lazy val glyph_chevron_right: Glyphicon = toGlyphicon("glyphicon glyphicon-chevron-right")
    lazy val glyph_menu_hamburger: Glyphicon = toGlyphicon("glyphicon glyphicon-menu-hamburger")
    lazy val caret: ModifierSeq = toClass("caret")

    //NAVBARS
    lazy val nav: Navbar = toClass("nav")
    lazy val navbar: Navbar = toClass("navbar")
    lazy val navbar_nav: Navbar = toClass("navbar-nav")
    lazy val navTabs: Navbar = toClass("nav-tabs")
    lazy val navbar_default: Navbar = toClass("navbar-default")
    lazy val navbar_inverse: Navbar = toClass("navbar-inverse")
    lazy val navbar_staticTop: Navbar = toClass("navbar-static-top")
    lazy val navbar_fixedTop: Navbar = toClass("navbar-fixed-top")
    lazy val navbar_pills: Navbar = toClass("nav-pills")
    lazy val navbar_form: Navbar = toClass("navbar-form")
    lazy val navbar_right: Navbar = toClass("navbar-right")
    lazy val navbar_left: Navbar = toClass("navbar-left")
    lazy val navbar_header: Navbar = toClass("navbar-header")
    lazy val navbar_brand: Navbar = toClass("navbar-brand")
    lazy val navbar_btn: Navbar = toClass("navbar-btn")
    lazy val navbar_collapse: Navbar = toClass("navbar-collapse")

    //LABELS
    lazy val label_default: LabelStyle = toLabel("label-default")
    lazy val label_primary: LabelStyle = toLabel("label-primary")
    lazy val label_success: LabelStyle = toLabel("label-success")
    lazy val label_info: LabelStyle = toLabel("label-info")
    lazy val label_warning: LabelStyle = toLabel("label-warning")
    lazy val label_danger: LabelStyle = toLabel("label-danger")
    lazy val black_label: LabelStyle = toLabel("black-label")

    lazy val controlLabel: ModifierSeq = toClass("control-label")


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
    lazy val btnToolbar: ModifierSeq = toClass("btn-toolbar")


    //ALERTS
    lazy val alert_success: AlertStyle = toAlert("alert-success")
    lazy val alert_info: AlertStyle = toAlert("alert-info")
    lazy val alert_warning: AlertStyle = toAlert("alert-warning")
    lazy val alert_danger: AlertStyle = toAlert("alert-danger")

    //MODALS
    lazy val modal: ModifierSeq = toClass("modal")
    lazy val fade: ModifierSeq = toClass("fade")
    lazy val modalDialog: ModifierSeq = toClass("modal-dialog")
    lazy val modalContent: ModifierSeq = toClass("modal-content")
    lazy val modalHeader: ModifierSeq = toClass("modal-header")
    lazy val modalInfo: ModifierSeq = toClass("modal-info")
    lazy val modalBody: ModifierSeq = toClass("modal-body")
    lazy val modalFooter: ModifierSeq = toClass("modal-footer")

    //NAVS
    lazy val tabsClass: NavStyle = toNav("nav-tabs")
    lazy val justified_tabs: NavStyle = toNav("nav-tabs nav-justified")
    lazy val pills: NavStyle = toNav("nav-pills")
    lazy val stacked_pills: NavStyle = toNav("nav-pills nav-stacked")
    lazy val justified_pills: NavStyle = toNav("nav-pills nav-justified")
    lazy val inline_list: NavStyle = toNav("list-inline")
    lazy val nav_bar: NavStyle = toNav("navbar-nav")
    lazy val regular_nav: NavStyle = toNav("nav-list")
    lazy val panel_nav: NavStyle = toNav("panel panel-primary")
    lazy val presentation_role: ModifierSeq = role := "presentation"
    lazy val tab_panel_role: ModifierSeq = role := "tabpanel"
    lazy val tab_list_role: ModifierSeq = role := "tablist"
    lazy val tab_role: ModifierSeq = role := "tab"
    lazy val tab_content: ModifierSeq = toClass("tab-content")
    lazy val tab_pane: ModifierSeq = toClass("tab-pane")
    lazy val nav_item: ModifierSeq = toClass("nav-item")
    lazy val nav_link: ModifierSeq = toClass("nav-link")

    //TABLES
    lazy val header_no_color: ModifierSeq = emptyMod
    lazy val default_header: ModifierSeq = toClass("thead-default")
    lazy val default_inverse: ModifierSeq = toClass("thead-inverse")
    lazy val default_table: TableStyle = toTable("")
    lazy val inverse_table: TableStyle = toTable("table-inverse")
    lazy val striped_table: TableStyle = toTable("table-striped")
    lazy val bordered_table: TableStyle = toTable("table-bordered")
    lazy val hover_table: TableStyle = toTable("table-hover")

    //GRIDS
    def colMD(nbCol: Int): ModifierSeq = toClass(s"col-md-$nbCol")

    def colMDOffset(offsetSize: Int): ModifierSeq = toClass(s"col-md-offset-$offsetSize")

    lazy val row: ModifierSeq = toClass("row")


    //PANELS
    lazy val panelClass: ModifierSeq = toClass("panel")
    lazy val panelDefault: ModifierSeq = toClass("panel-default")
    lazy val panelHeading: ModifierSeq = toClass("panel-heading")
    lazy val panelTitle: ModifierSeq = toClass("panel-title")
    lazy val panelBody: ModifierSeq = toClass("panel-body")


    //TABLES
    lazy val tableClass: ModifierSeq = toClass("table")
    lazy val bordered: ModifierSeq = toClass("table-bordered")
    lazy val striped: ModifierSeq = toClass("table-striped")
    lazy val active: ModifierSeq = toClass("active")
    lazy val success: ModifierSeq = toClass("success")
    lazy val danger: ModifierSeq = toClass("danger")
    lazy val warning: ModifierSeq = toClass("warning")
    lazy val info: ModifierSeq = toClass("info")


    //INPUTS
    lazy val inputGroup: ModifierSeq = toClass("input-group")
    lazy val inputGroupButtonClass: ModifierSeq = toClass("input-group-btn")
    lazy val inputGroupAddonClass: ModifierSeq = toClass("input-group-addon")

    //FORMS
    lazy val formControl: ModifierSeq = toClass("form-control")
    lazy val formGroup: ModifierSeq = toClass("form-group")
    lazy val formInline: ModifierSeq = toClass("form-inline")
    lazy val formHorizontal: ModifierSeq = toClass("form-horizontal")
    lazy val formVertical: ModifierSeq = toClass("form-vertical")


    //OTHERS
    lazy val dropdown: ModifierSeq = toClass("dropdown")
    lazy val dropdownMenu: ModifierSeq = toClass("dropdown-menu")
    lazy val dropdownToggle: ModifierSeq = toClass("dropdown-toggle")
    lazy val progress: ModifierSeq = toClass("progress")
    lazy val progressBar: ModifierSeq = toClass("progress-bar")
    lazy val container: ModifierSeq = toClass("container")
    lazy val jumbotron: ModifierSeq = toClass("jumbotron")
    lazy val themeShowcase: ModifierSeq = toClass("theme-showcase")
    lazy val controlGroup: ModifierSeq = toClass("control-group")
    lazy val controls: ModifierSeq = toClass("controls")
  }

}

package stylesheet2 {

  package object bootstrap2 extends Bootstrap2Package

  trait Bootstrap2Package {

    //Exclusive Button Group
    lazy val stringInGroup: ModifierSeq = Seq(
      sty.height := "30px",
      sty.paddingTop := "3px",
      sty.paddingLeft := "6px",
      sty.paddingRight := "6px"
    )

    lazy val twoGlyphButton: ModifierSeq = Seq(
      sty.top := "1px",
      sty.height := "30px"
    )

    lazy val stringButton: ModifierSeq = Seq(
      sty.top := "4px",
      sty.height := "30px"
    )

    lazy val collapseTransition: ModifierSeq = Seq(
      transition := "height .3s",
      height := 0,
      overflow := "hidden"
    )

  }

}