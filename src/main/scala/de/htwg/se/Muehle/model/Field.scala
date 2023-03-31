package de.htwg.se.Muehle.model

case class Field(
    matrix: Vector[Vector[Stone]]
) {
  def this(allowedFields: Set[(Int, Int)]) = {
    this(
      Vector.tabulate(7, 7) {
        case (x, y) if allowedFields.contains((x, y)) => Stone.Empty
        case _                                        => Stone.Vorbitten
      }
    )
  }
  def cells(row: Int): String = {
    matrix(row)
      .map(_.toString)
      .map(name =>
        s"%-${Stone.values.map(_.toString).maxBy(_.length).length}s"
          .format(name)
      )
      .mkString("|")
  }

  def field(): String = {
    (0 to 6)
      .map { row =>
        cells(row)
      }
      .mkString("\n")
  }

  def put(stone: Stone, x: Int, y: Int): Field = {
    copy(matrix.updated(x, matrix(x).updated(y, stone)))
  }

  def get(x: Int, y: Int): Stone = matrix(x)(y)
}
