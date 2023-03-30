import de.htwg.se.Muehle.model.{Stone, Field}
import de.htwg.se.Muehle.model.Pattern

@main def hello: Unit = {
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
  val matri = new Pattern[Stone](
    7,
    Stone.Empty,
    Stone.Vorbitten,
    allowedFields
  )
  println("start of the programm")
  println("print the value of the matrix")
  println(matri.cell(1, 1))
  println("print a row auf a Matrix")
  println(matri.row(0))
  println("Chance a value of a field")
  matri.replaceCell(1, 2, Stone.White)

  val d = Field(matri, allowedFields)
  println("game board output")
  println(d.field())
  println()
  println()
  println()
  println("Change a value of a field")
  val s = d.put(Stone.Black, 1, 1)
  println("game board output")
  println(s.field())
  println("Return a Stone")
  println(s.get(1, 1))
}
