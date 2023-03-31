enum Stone(string: String):
  override def toString = string
  case White extends Stone("WHITE")
  case Black extends Stone("BLACK")
  case Empty extends Stone("EMPTY")
  case Vorbitten extends Stone("")

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
) // Beispiel Set-Objekt
val d =
  new Field(
    allowedFields
  )

d.field()
d.get(1, 1)
val d1 = d.put(Stone.Black, 1, 1)
d1.get(1, 1)
