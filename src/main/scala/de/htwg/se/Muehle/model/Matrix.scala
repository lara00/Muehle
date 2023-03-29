package de.htwg.se.Muehle.model
package de.htwg.se.Muehle.AllowedFields

case class Matrix[T](rows: Vector[Vector[T]], allowedFields: Set[(Int, Int)]):
  def this(
      size: Int,
      filling: T,
      vorbitten: T,
      allowedFields: Set[(Int, Int)]
  ) =
    this(
      Vector.tabulate(size, size) {
        case (x, y) if allowedFields.contains((x, y)) => filling
        case _                                        => vorbitten
      },
      allowedFields
    )

  val size: Int = rows.size

  def cell(row: Int, col: Int): T = rows(row)(col)

  def row(row: Int) = rows(row)

  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = copy(
    rows.updated(row, rows(row).updated(col, cell))
  )
