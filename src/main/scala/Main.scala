object Main {
  def main(args: Array[String]): Unit = {
    println(MaxLengthString[5]("hello there"))
    println(MaxLengthString[5]("hello"))

    println(MaxLengthString.unsafe[5]("hello there"))
    println(MaxLengthString.unsafe[5]("hello"))

    println(MaxLengthString.safe[5]("hello"))
//    println(MaxLengthString.safe[5]("hello there"))
  }
}
