package de.htwg.se.Muehle.model
object field {
  val erlaubteFelder = Set(
    (0, 0),
    (0, 3),
    (0, 6),
    (1, 1),
    (1, 3),
    (1, 5),
    (2, 2),
    (2, 3),
    (2, 4),
    (3, 0),
    (3, 1),
    (3, 2),
    (3, 4),
    (3, 5),
    (3, 6),
    (4, 2),
    (4, 3),
    (4, 4),
    (5, 1),
    (5, 3),
    (5, 5),
    (6, 0),
    (6, 3),
    (6, 6)
  )
  def isAllowed(row: Int, column: Int): Boolean =
    erlaubteFelder.contains((row, column))
}
case class field(matrix: Matrix[Stone]):
  def this(size: Int, filling: Stone, vorbitten: Stone) =
    this(new Matrix(size, filling, vorbitten))
  val size = matrix.size
  def cells(row: Int): String =
    matrix
      .row(row)
      .map(_.toString)
      .map(name =>
        s"%-${Stone.values.map(_.toString).maxBy(_.length).length}s"
          .format(name)
      )
      .mkString("|")
  def mesh(): String = {
    (0 to 6)
      .map { row =>
        cells(row)
      }
      .mkString("\n")
  }
  def put(stone: Stone, x: Int, y: Int): field = {
    val newMatrix = matrix.replaceCell(x, y, stone)
    field(newMatrix)
  }
  def get(x: Int, y: Int): Stone = matrix.cell(x, y)
