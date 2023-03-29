package de.htwg.se.Muehle.model

case class Matrix[T](rows: Vector[Vector[T]]):
  def this(size: Int, filling: T, vorboten: T) = this(
    Vector
      .tabulate(7, 7) {
        case (x, y) if Field.isAllowed(x, y) => filling
        case _                               => vorboten
      }
  )
  val size: Int = rows.size
  def cell(row: Int, col: Int): T = rows(row)(col)
  def row(row: Int) = rows(row)
  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = copy(
    rows.updated(row, rows(row).updated(col, cell))
  )
