import de.htwg.se.Muehle.model.{Matrix, Stone, Field}

@main def hello: Unit = {
  val matri = new Matrix[Stone](7, Stone.Empty, Stone.Vorbitten)
  println("start of the programm")
  println("print the value of the matrix")
  println(matri.cell(1, 1))
  println("print a row auf a Matrix")
  println(matri.rows(0))
  println("Chance a value of a field")
  matri.replaceCell(1, 2, Stone.White)

  val d = Field(matri)
  println("game board output")
  println(d.mesh())
  println()
  println()
  println()
  println("Chance a value of a field")
  val s = d.put(Stone.Black, 1, 1)
  println("game board output")
  println(s.mesh())
  println("Return a Stone")
  println(s.get(1, 1))
}
