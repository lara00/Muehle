package de.htwg.se.Muehle.model

case class Field(matrix: Vector[Vector[Stone]] = Vector.tabulate(7, 7) {
  case (x, y) if Field.allowedFields.contains((x, y)) => Stone.Empty
  case _                                              => Stone.Vorbitten
}) {
  def cells(x: Int): String = {
    matrix(x)
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
    val updatedRow = matrix(x).updated(y, stone)
    val updatedMatrix = matrix.updated(x, updatedRow)
    copy(matrix = updatedMatrix)
  }

  def get(x: Int, y: Int): Stone = matrix(x)(y)
}

object Field {
  val allowedFields = Set(
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
}
