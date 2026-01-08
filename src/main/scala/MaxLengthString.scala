import scala.compiletime.constValue
import scala.quoted.{Expr, Quotes}

case class MaxLengthString[N <: Int] private(s: String)

object MaxLengthString {
  def apply[N<:Int: ValueOf](s: String): Either[Exception, MaxLengthString[N]] = {
    val n = valueOf[N]
    Either.cond(s.length < n, new MaxLengthString[N](s), Exception(s"Invalid string $s of value $n}"))
  }

  def unsafe[N<:Int](s:String): MaxLengthString[N] = {
    new MaxLengthString[N](s)
  }

  import scala.quoted.* // imports Quotes, Expr

  inline def safe[N<:Int](inline s: String): MaxLengthString[N] = ${safeImpl[N]('{s}, '{constValue[N]})}

  def safeImpl[N<:Int: Type](s: Expr[String], n: Expr[Int])(using q: Quotes): Expr[MaxLengthString[N]] = {
    import q.reflect.*
    val sLength = s.valueOrAbort.length
    val maxLength = n.valueOrAbort
    if(sLength<=maxLength) '{MaxLengthString.unsafe[N](${s})}
    else report.errorAndAbort(s"Invalid string ${s.valueOrAbort} with length $sLength for maxLength $maxLength")
  }
  
}
