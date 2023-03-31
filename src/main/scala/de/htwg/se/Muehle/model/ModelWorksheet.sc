enum Stone(stringRepresentation: String):
  override def toString = stringRepresentation
  case White extends Stone("WHITE")
  case Black extends Stone("BLACK")
  case Empty extends Stone("EMPTY")
  case Vorbitten extends Stone("")

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

case class Matrix[T](rows: Vector[Vector[T]]):
  def this(size: Int, filling: T, vorboten: T) = this(
    Vector
      .tabulate(7, 7) {
        case (x, y) if field.isAllowed(x, y) => filling
        case _                               => vorboten
      }
  )
  val size: Int = rows.size
  def cell(row: Int, col: Int): T = rows(row)(col)
  def row(row: Int) = rows(row)
  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = copy(
    rows.updated(row, rows(row).updated(col, cell))
  )

val matri = new Matrix[Stone](7, Stone.Empty, Stone.Vorbitten)
matri.cell(1, 1)
matri.rows(0)
matri.replaceCell(1, 2, Stone.White)

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
    field(matrix.replaceCell(x, y, stone))
  }
  def get(x: Int, y: Int): Stone = matrix.cell(x, y)

val d = field(matri)
print(d.size)
d.mesh()
val s = d.put(Stone.Black, 1, 1)
s.mesh()
s.get(1, 1)
s.get(1, 3)
