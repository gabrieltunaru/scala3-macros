import scala.deriving.Mirror
import compiletime.*

trait SpaceSeparatedString[A] {
  def serialize(a: A): String
}

object SpaceSeparatedString {
  inline def serializeTuple[E <: Tuple](elements: E): List[String] =
    inline elements match {
      case EmptyTuple     => List()
      case el: (eh *: et) =>
        val h *: t = el
        val value  = summonInline[SpaceSeparatedString[eh]].serialize(h)
        value :: serializeTuple[et](t)
    }

  given SpaceSeparatedString[String] with
    override def serialize(a: String): String = a

  inline def derived[A <: Product](using
      m: Mirror.ProductOf[A]
  ): SpaceSeparatedString[A] = {
    new SpaceSeparatedString[A] {
      override def serialize(a: A): String = {
        val valueTuple = Tuple.fromProductTyped(a)
        val fieldReprs =
          serializeTuple[m.MirroredElemTypes](valueTuple)
        fieldReprs.mkString
      }
    }
  }
}
