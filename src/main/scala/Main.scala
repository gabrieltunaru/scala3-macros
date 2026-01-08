import Person.{MaxLen1, MaxLen2}

object Main {
  def main(args: Array[String]): Unit = {
    println(MaxLengthString[5]("hello there"))
    println(MaxLengthString[5]("hello"))

    println(MaxLengthString.unsafe[5]("hello there"))
    println(MaxLengthString.unsafe[5]("hello"))

    println(MaxLengthString.safe[5]("hello"))
    //    println(MaxLengthString.safe[5]("hello there"))
    //    val s = MaxLengthString.


    val space = SpaceSeparatedString.derived[Person]
    val p1 = Person(MaxLengthString.safe[2]("12"), MaxLengthString.safe[1]("M"), MaxLengthString.safe[20]("gabriel"), MaxLengthString.safe[6]("am57xc"))
    println(space.serialize(p1))

  }
}
