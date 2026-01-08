case class Person(
    age: MaxLengthString[2],
    gender: MaxLengthString[1],
    name: MaxLengthString[20],
    corporateKey: MaxLengthString[6]
)
