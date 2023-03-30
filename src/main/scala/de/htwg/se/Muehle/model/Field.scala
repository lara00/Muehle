package de.htwg.se.Muehle.model

case class Field(matrix: Pattern[Stone], allowedFields: Set[(Int, Int)]) {
  def this(
      size: Int,
      filling: Stone,
      vorbitten: Stone,
      allowedFields: Set[(Int, Int)]
  ) =
    this(new Pattern(size, filling, vorbitten, allowedFields), allowedFields)
  def cells(row: Int): String =
    matrix
      .row(row)
      .map(_.toString)
      .map(name =>
        s"%-${Stone.values.map(_.toString).maxBy(_.length).length}s"
          .format(name)
      )
      .mkString("|")
  def field(): String = {
    (0 to 6)
      .map { row =>
        cells(row)
      }
      .mkString("\n")
  }
  def put(stone: Stone, x: Int, y: Int): Field = {
    val newMatrix = matrix.replaceCell(x, y, stone)
    Field(newMatrix, allowedFields)
  }
  def get(x: Int, y: Int): Stone = matrix.cell(x, y)
}
