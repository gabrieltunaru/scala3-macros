import scala.deriving.Mirror
import compiletime.*

trait SpaceSeparatedString[A] {
  def serialize(a: A): String
}

object SpaceSeparatedString {
 //TODO: replace
  inline def showTuple[E <: Tuple, L <: Tuple](elements: E): List[String] =
    inline (elements, erasedValue[L]) match { // (("Daniel", 99, true), ("name", "age", "programmer"))
      case (EmptyTuple, EmptyTuple) => List()
      case (el: (eh *: et), lab: (lh *: lt)) =>
        val (h *: t) = el // h = "Daniel", t = (99, true)
        val label = constValue[lh] // label = "name"
        val value = summonInline[SpaceSeparatedString[eh]].serialize(h) // Show[String].show("Daniel")

        (value) :: showTuple[et, lt](t)
      // "name: Daniel" :: showTuple[(Int, Boolean), ("age", "programmer")]((99, true))
    }
//
//  inline def derived[N <: Int : ValueOf](using m: Mirror.ProductOf[MaxLengthString[N]]): SpaceSeparatedString[MaxLengthString[N]] = {
//    new SpaceSeparatedString[MaxLengthString[N]] {
//      override def serialize(a: MaxLengthString[N]): String = {
//        val valueTuple = Tuple.fromProductTyped(a)
//        val n = valueOf[N]
//        val truncated = a.s.take(n)
//        val spaces = " " * (n - truncated.length)
//        truncated + spaces
//      }
//    }
//  }

  given SpaceSeparatedString[String] with
    override def serialize(a: String): String = a


  inline def derived[A <: Product](using m: Mirror.ProductOf[A]): SpaceSeparatedString[A] = {
    new SpaceSeparatedString[A] {
      override def serialize(a: A): String =  {
        val valueTuple = Tuple.fromProductTyped(a)
        val fieldReprs = showTuple[m.MirroredElemTypes, m.MirroredElemLabels](valueTuple)
        fieldReprs.mkString
      }
    }
  }
}
