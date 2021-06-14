package scaladget.tools

object Stylesheet extends Stylesheet
import com.raquo.laminar.api.L._

trait Stylesheet {

//    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
//
//    type ClassAttrPair = scalatags.generic.AttrPair[dom.Element, String]
//    type ModifierSeq = Seq[Modifier]
//    val emptyMod: ModifierSeq = Seq()
//
//
//    def pairing(a: String, b: String): ClassAttrPair = `class` := (a.split(" ") ++ b.split(" ")).distinct.mkString(" ")
//
//    implicit def modifierToModifierSeq(p: Modifier): ModifierSeq = Seq(p)
//
//    implicit def stringToModifierSeq(classString: String): ModifierSeq = toClass(classString)
//
//    implicit def classAttrPairToModifierSeq(classAttrPair: ClassAttrPair): ModifierSeq = Seq(classAttrPair)
//
//    implicit class stylePairToModifierSeq(sPair: StylePair[dom.Element, _]) {
//      def toMS: ModifierSeq = Seq(sPair)
//    }
//
//    implicit class stylePairSeqToModifierSeq(sPair: Seq[StylePair[dom.Element, _]]) {
//      def toMS: ModifierSeq = sPair
//    }
//
//    implicit def rxToModifierSeq(dyn: Rx.Dynamic[ModifierSeq]): ModifierSeq = dyn.now
//
//    implicit class ComposableClassAttrPair[P <: ClassAttrPair](pair: P) {
//      def +++(pair2: ClassAttrPair): ModifierSeq = {
//        if (pair.a.name != "class" || pair2.a.name != "class") toClass("ClassError")
//        else pairing(pair.v, pair2.v)
//      }
//
//      def +++(mod: ModifierSeq): ModifierSeq = mod +++ pair
//
//      def +++(sty: StylePair[dom.Element, _]): ModifierSeq = Seq(pair, sty)
//    }
//
//    implicit class ComposableModifierSeq(modifierSeq: ModifierSeq) {
//      private def findClassAttrPair(modifierSeq: ModifierSeq) =
//        modifierSeq.collect { case a: ClassAttrPair => a }.filter { x =>
//          x.a.name == "class"
//        }
//
//
//      def +++(classPair: ClassAttrPair): ModifierSeq = {
//        val attrPair = findClassAttrPair(modifierSeq)
//
//        if (attrPair.isEmpty) modifierSeq :+ classPair
//        else modifierSeq.filterNot(_ == attrPair.head) :+ pairing(attrPair.head.v, classPair.v)
//      }
//
//      def +++(modifierSeq2: ModifierSeq): ModifierSeq = {
//        val attrPair = findClassAttrPair(modifierSeq)
//        val attrPair2 = findClassAttrPair(modifierSeq2)
//        val classPairing =
//          if (attrPair.isEmpty && attrPair2.isEmpty) Seq()
//          else Seq((attrPair ++ attrPair2).reduce { (a, b) => pairing(a.v, b.v) })
//
//        modifierSeq.filterNot {
//          attrPair.contains(_)
//        } ++ modifierSeq2.filterNot {
//          attrPair2.contains(_)
//        } ++ classPairing
//
//      }
//
//      //def +++(dyn: Rx.Dynamic[ModifierSeq]): ModifierSeq = dyn.flatMap { d =>
//      // modifierSeq +++ d
//      //  }
//      def +++(dyn: Rx.Dynamic[ModifierSeq]): ModifierSeq = modifierSeq +++ dyn.now
//
//      def divCSS(cssClass: String) = div(`class` := cssClass)
//    }
//
//
//    implicit def condOnModifierSeq3(t: Tuple3[Boolean, ModifierSeq, ModifierSeq]): ModifierSeq = if (t._1) t._2 else t._3
//
//    implicit def condOnModifierSeq2(t: Tuple2[Boolean, ModifierSeq]): ModifierSeq = condOnModifierSeq3(t._1, t._2, emptyMod)
//
//
//    def toClass(s: String): ModifierSeq = `class` := s
//
//    // Explicit builders for ModifierSeq (from string or from condition and two ModifierSeq alternatives)
//    def ms(s: String): ModifierSeq = Seq(`class` := s)
//
//    def ms(cond: Boolean, ms1: ModifierSeq, ms2: ModifierSeq = emptyMod): ModifierSeq = condOnModifierSeq3((cond, ms1, ms2))

    // CONVINIENT GENERAL ALIASES


   // lazy val passwordType = `type` := "password"

}