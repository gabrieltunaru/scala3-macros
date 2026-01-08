import Person.{MaxLen1, MaxLen2}

import scala.deriving.Mirror

case class Person(
                   age: MaxLengthString[2],
                   gender: MaxLengthString[1],
                   name: MaxLengthString[20],
                   corporateKey: MaxLengthString[6],
//                   age: MaxLen2,
//                   gender: MaxLen1,

                 )

object Person {
  opaque type MaxLen2 = MaxLengthString[2]
  opaque type MaxLen1 = MaxLengthString[1]

}