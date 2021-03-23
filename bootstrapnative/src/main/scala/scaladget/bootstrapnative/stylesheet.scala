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


import com.raquo.laminar.api.L._

package stylesheet {

  import com.raquo.laminar.nodes.{ReactiveElement, ReactiveHtmlElement}

  //package object bootstrap extends BootstrapPackage

  trait BootstrapPackage {

    private def toGlyphicon(s: String) = cls(s"glyphicon $s")

    private def toLabel(s: String) = cls(s"label $s")

    private def toButton(s: String) = cls(s"btn $s")

    private def toAlert(s: String) = cls(s"alert $s")

    private def toNav(s: String) = cls(s"nav $s")

    private def toTable(s: String) = cls(s"table $s")

    //GHYPHICONS
    lazy val glyph_edit = toGlyphicon("glyphicon-pencil")
    lazy val glyph_edit2 = toGlyphicon("glyphicon-edit")
    lazy val glyph_save = toGlyphicon("glyphicon-save")
    lazy val glyph_trash = toGlyphicon("glyphicon-trash")
    lazy val glyph_plus = toGlyphicon("glyphicon-plus")
    lazy val glyph_plus_sign = toGlyphicon("glyphicon-plus-sign")
    lazy val glyph_minus_sign = toGlyphicon("glyphicon-minus-sign")
    lazy val glyph_minus = toGlyphicon("glyphicon-minus")
    lazy val glyph_ok = toGlyphicon("glyphicon-ok")
    lazy val glyph_question = toGlyphicon("glyphicon-question-sign")
    lazy val glyph_file = toGlyphicon("glyphicon-file")
    lazy val glyph_folder_close = toGlyphicon("glyphicon-folder-close")
    lazy val glyph_home = toGlyphicon("glyphicon-home")
    lazy val glyph_upload = toGlyphicon("glyphicon-cloud-upload")
    lazy val glyph_download = toGlyphicon("glyphicon-cloud-download")
    lazy val glyph_download_alt = toGlyphicon("glyphicon-download-alt")
    lazy val glyph_settings = toGlyphicon("glyphicon-cog")
    lazy val glyph_off = toGlyphicon("glyphicon-off")
    lazy val glyph_flash = toGlyphicon("glyphicon-flash")
    lazy val glyph_flag = toGlyphicon("glyphicon-flag")
    lazy val glyph_remove = toGlyphicon("glyphicon-remove-sign")
    lazy val glyph_road = toGlyphicon("glyphicon-road")
    lazy val glyph_fire = toGlyphicon("glyphicon-fire")
    lazy val glyph_list = toGlyphicon("glyphicon-list")
    lazy val glyph_stats = toGlyphicon("glyphicon-stats")
    lazy val glyph_refresh = toGlyphicon("glyphicon-refresh")
    lazy val glyph_repeat = toGlyphicon("glyphicon-repeat")
    lazy val glyph_lock = toGlyphicon("glyphicon-lock")
    lazy val glyph_archive = toGlyphicon("glyphicon-compressed")
    lazy val glyph_market = toGlyphicon("glyphicon-shopping-cart")
    lazy val glyph_info = toGlyphicon("glyphicon-info-sign")
    lazy val glyph_plug = toGlyphicon("icon-plug")
    lazy val glyph_exclamation = toGlyphicon("glyphicon-exclamation-sign")
    lazy val glyph_comment = toGlyphicon("glyphicon-comment")
    lazy val glyph_upload_alt = toGlyphicon("glyphicon-upload")
    lazy val glyph_arrow_right = toGlyphicon("glyphicon-arrow-right")
    lazy val glyph_arrow_left = toGlyphicon("glyphicon-arrow-left")
    lazy val glyph_arrow_right_and_left = toGlyphicon("glyphicon-resize -horizontal")
    lazy val glyph_filter = toGlyphicon("glyphicon-filter")
    lazy val glyph_copy = toGlyphicon("glyphicon-copy")
    lazy val glyph_paste = toGlyphicon("glyphicon-paste")
    lazy val glyph_time = toGlyphicon("glyphicon-time")
    lazy val glyph_alph_sorting = toGlyphicon("glyphicon-sort-by-alphabet")
    lazy val glyph_sort_by_attributes = toGlyphicon("glyphicon-sort-by-attributes")
    lazy val glyph_sort_by_attributes_alt = toGlyphicon("glyphicon-sort-by-attributes-alt")
    lazy val glyph_triangle_bottom = toGlyphicon("glyphicon-triangle-bottom")
    lazy val glyph_triangle_top = toGlyphicon("glyphicon-triangle-top")
    lazy val glyph_chevron_left = toGlyphicon("glyphicon glyphicon-chevron-left")
    lazy val glyph_chevron_right = toGlyphicon("glyphicon glyphicon-chevron-right")
    lazy val glyph_menu_hamburger = toGlyphicon("glyphicon glyphicon-menu-hamburger")
    lazy val caret = cls("caret")

    //NAVBARS
    lazy val nav  = cls("nav")
    lazy val navbar  = cls("navbar")
    lazy val navbar_nav  = cls("navbar-nav")
    lazy val navTabs  = cls("nav-tabs")
    lazy val navbar_default  = cls("navbar-default")
    lazy val navbar_inverse  = cls("navbar-inverse")
    lazy val navbar_staticTop  = cls("navbar-static-top")
    lazy val navbar_fixedTop  = cls("navbar-fixed-top")
    lazy val navbar_pills  = cls("nav-pills")
    lazy val navbar_form  = cls("navbar-form")
    lazy val navbar_right  = cls("navbar-right")
    lazy val navbar_left  = cls("navbar-left")
    lazy val navbar_header  = cls("navbar-header")
    lazy val navbar_brand  = cls("navbar-brand")
    lazy val navbar_btn  = cls("navbar-btn")
    lazy val navbar_collapse  = cls("navbar-collapse")

    //LABELS
    lazy val label_default  = toLabel("label-default")
    lazy val label_primary  = toLabel("label-primary")
    lazy val label_success  = toLabel("label-success")
    lazy val label_info  = toLabel("label-info")
    lazy val label_warning  = toLabel("label-warning")
    lazy val label_danger  = toLabel("label-danger")
    lazy val black_label  = toLabel("black-label")

    lazy val controlLabel = cls("control-label")


    //BUTTONS
    lazy val btn = cls("btn")
    lazy val btn_default = toButton("btn-default")
    lazy val btn_primary = toButton("btn-primary")
    lazy val btn_success = toButton("btn-success")
    lazy val btn_info = toButton("btn-info")
    lazy val btn_warning = toButton("btn-warning")
    lazy val btn_danger = toButton("btn-danger")
    lazy val btn_large = toButton("btn-lg")
    lazy val btn_medium = toButton("btn-md")
    lazy val btn_small = toButton("btn-sm")
    lazy val btn_test = toButton("myButton")
    lazy val btn_right = toButton("pull-right")

    lazy val btnGroup = cls("btn-group")
    lazy val btnToolbar = cls("btn-toolbar")


    //ALERTS
    lazy val alert_success = toAlert("alert-success")
    lazy val alert_info = toAlert("alert-info")
    lazy val alert_warning = toAlert("alert-warning")
    lazy val alert_danger = toAlert("alert-danger")

    //MODALS
    lazy val modal = cls("modal")
    lazy val fade = cls("fade")
    lazy val modalDialog = cls("modal-dialog")
    lazy val modalContent = cls("modal-content")
    lazy val modalHeader = cls("modal-header")
    lazy val modalInfo = cls("modal-info")
    lazy val modalBody = cls("modal-body")
    lazy val modalFooter = cls("modal-footer")

    //NAVS
    lazy val tabsClass = toNav("nav-tabs")
    lazy val justified_tabs = toNav("nav-tabs nav-justified")
    lazy val pills = toNav("nav-pills")
    lazy val stacked_pills = toNav("nav-pills nav-stacked")
    lazy val justified_pills = toNav("nav-pills nav-justified")
    lazy val inline_list = toNav("list-inline")
    lazy val nav_bar = toNav("navbar-nav")
    lazy val regular_nav = toNav("nav-list")
    lazy val panel_nav = toNav("panel panel-primary")
    lazy val presentation_role = role := "presentation"
    lazy val tab_panel_role = role := "tabpanel"
    lazy val tab_list_role = role := "tablist"
    lazy val tab_role = role := "tab"
    lazy val tab_content = cls("tab-content")
    lazy val tab_pane = cls("tab-pane")
    lazy val nav_item = cls("nav-item")
    lazy val nav_link = cls("nav-link")

    //TABLES
    lazy val header_no_color = emptyMod
    lazy val default_header = cls("thead-default")
    lazy val default_inverse = cls("thead-inverse")
    lazy val default_table = toTable("")
    lazy val inverse_table = toTable("table-inverse")
    lazy val striped_table = toTable("table-striped")
    lazy val bordered_table = toTable("table-bordered")
    lazy val hover_table = toTable("table-hover")

    //GRIDS
    def colMD(nbCol: Int) = cls(s"col-md-$nbCol")

    def colMDOffset(offsetSize: Int) = cls(s"col-md-offset-$offsetSize")

    lazy val row = cls("row")


    //PANELS
    lazy val panelClass = cls("panel")
    lazy val panelDefault = cls("panel-default")
    lazy val panelHeading = cls("panel-heading")
    lazy val panelTitle = cls("panel-title")
    lazy val panelBody = cls("panel-body")


    //TABLES
    lazy val tableClass = cls("table")
    lazy val bordered = cls("table-bordered")
    lazy val striped = cls("table-striped")
    lazy val active = cls("active")
    lazy val success = cls("success")
    lazy val danger = cls("danger")
    lazy val warning = cls("warning")
    lazy val info = cls("info")


    //INPUTS
    lazy val inputGroup = cls("input-group")
    lazy val inputGroupButtonClass = cls("input-group-btn")
    lazy val inputGroupAddonClass = cls("input-group-addon")

    //FORMS
    lazy val formControl = cls("form-control")
    lazy val formGroup = cls("form-group")
    lazy val formInline = cls("form-inline")
    lazy val formHorizontal = cls("form-horizontal")
    lazy val formVertical = cls("form-vertical")


    //OTHERS
    lazy val dropdown = cls("dropdown")
    lazy val dropdownMenu = cls("dropdown-menu")
    lazy val dropdownToggle = cls("dropdown-toggle")
    lazy val progress = cls("progress")
    lazy val progressBar = cls("progress-bar")
    lazy val container = cls("container")
    lazy val jumbotron = cls("jumbotron")
    lazy val themeShowcase = cls("theme-showcase")
    lazy val controlGroup = cls("control-group")
    lazy val controls = cls("controls")
  }

}

package stylesheet2 {

  package object bootstrap2 extends Bootstrap2Package

  trait Bootstrap2Package {

    //Exclusive Button Group
    lazy val stringInGroup = Seq(
      height := "30px",
      paddingTop := "3px",
      paddingLeft := "6px",
      paddingRight := "6px"
    )

    lazy val twoGlyphButton = Seq(
      top := "1px",
      height := "30px"
    )

    lazy val stringButton = Seq(
      top := "4px",
      height := "30px"
    )

    lazy val collapseTransition = Seq(
      transition := "height .3s",
      height := "0",
      overflow := "hidden"
    )

  }

}