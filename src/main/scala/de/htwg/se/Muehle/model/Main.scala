import de.htwg.se.Muehle.model.{Stone, Field}

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
  ) // Beispiel Set-Objekt
  val field =
    new Field(
      allowedFields
    )
  println("Gebe  Feld aus")
  println(" ")
  println(field.field())
}
