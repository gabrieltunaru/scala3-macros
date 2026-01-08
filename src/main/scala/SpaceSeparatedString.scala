import scala.deriving.Mirror
import compiletime.*

trait SpaceSeparatedString[A] {
  def serialize(a: A): String
}

object SpaceSeparatedString {
 //TODO: replace
  inline def showTuple[E <: Tuple, L <: Tuple](elements: E): List[String] =
    inline (elements, erasedValue[L]) match { 
      case (EmptyTuple, EmptyTuple) => List()
      case (el: (eh *: et), lab: (lh *: lt)) =>
        val (h *: t) = el 
        val label = constValue[lh]
        val value = summonInline[SpaceSeparatedString[eh]].serialize(h) 

        (value) :: showTuple[et, lt](t)
    }


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
