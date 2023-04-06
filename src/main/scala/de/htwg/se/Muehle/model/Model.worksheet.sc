enum Stone(string: String):
  override def toString = string
  case White extends Stone("WHITE")
  case Black extends Stone("BLACK")
  case Empty extends Stone("EMPTY")

val stones = (1 to 24).map { i =>
  i -> Stone.Empty
}

val e = 1 to 24
case class Field(
    fields: Map[Int, Stone] = (1 to 24).map(i => i -> Stone.Empty).toMap
) {
  private val maxStoneLength =
    Stone.values.map(_.toString).maxBy(_.length).length
  def setField(number: Int, value: Stone): Field =
    copy(fields = fields.updated(number, value))
  def stoneString(number: Int): String = fields(number).toString
  def formatRow(fields: String*): String = {
    fields.map(_.padTo(maxStoneLength, ' ')).mkString(" | ") + "\n"
  }
  private def row1_row7_format(
      a: Int = 1 | 22,
      b: Int = 2 | 23,
      c: Int = 3 | 24
  ): String =
    formatRow(stoneString(a), "", "", stoneString(b), "", "", stoneString(c))
  private def row2_row6_format(
      a: Int = 4 | 19,
      b: Int = 5 | 20,
      c: Int = 6 | 21
  ): String =
    formatRow("", stoneString(a), "", stoneString(b), "", stoneString(c), "")
  private def row3_row5_format(
      a: Int = 10 | 26,
      b: Int = 11 | 17,
      c: Int = 12 | 18
  ): String =
    formatRow("", "", stoneString(a), stoneString(b), stoneString(c), "", "")
  private def row4_format(
      a: Int = 10,
      b: Int = 11,
      c: Int = 12,
      d: Int = 13,
      e: Int = 14,
      f: Int = 15
  ): String =
    formatRow(
      stoneString(a),
      stoneString(b),
      stoneString(c),
      "",
      stoneString(d),
      stoneString(e),
      stoneString(f)
    )
  def printfeld(): String = {
    row1_row7_format(1, 2, 3)
      + row2_row6_format(4, 5, 6)
      + row3_row5_format(7, 8, 9)
      + row4_format() +
      row3_row5_format(16, 17, 18)
      + row2_row6_format(19, 20, 21)
      + row1_row7_format(22, 23, 24)

  }
}

object Field {
  val EmptyFields = (1 to 24).map(i => i -> Stone.Empty).toMap
}
val zahl = Stone.values.map(_.toString).maxBy(_.length).length
val field = Field()
val updatedField = field.setField(1, Stone.Black)

field.printfeld()
