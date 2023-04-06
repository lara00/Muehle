package de.htwg.se.Muehle.model

case class Field(
    fields: Map[Int, Stone] = {
      var m = Map.empty[Int, Stone]
      var e = 1 to 24
      for (i <- e) m += (i -> Stone.Empty)
      m
    }
) {
  private val maxStoneLength = 5
  def size: Int = fields.size
  def setField(number: Int, value: Stone): Field =
    copy(fields = fields.updated(number, value))
  def stoneString(number: Int): String = fields(number).toString
  def formatRow(fields: String*): String = {
    fields.map(_.padTo(maxStoneLength, ' ')).mkString(" | ") + "\n"
  }
  private def row1_row7_format(
      a: Int,
      b: Int,
      c: Int
  ): String =
    formatRow(stoneString(a), "", "", stoneString(b), "", "", stoneString(c))
  private def row2_row6_format(
      a: Int,
      b: Int,
      c: Int
  ): String =
    formatRow("", stoneString(a), "", stoneString(b), "", stoneString(c), "")
  private def row3_row5_format(
      a: Int,
      b: Int,
      c: Int
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
