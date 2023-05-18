package de.htwg.se.Muehle.model

/*Strategy Pattern*/
trait MillChecker {
  def isMill(number: Int, field: Field): Boolean
}

object VerticalAndHorizontalMillChecker extends MillChecker {
  private val verticalMills = Seq(
    Seq(1, 2, 3),
    Seq(4, 5, 6),
    Seq(7, 8, 9),
    Seq(10, 11, 12),
    Seq(13, 14, 15),
    Seq(16, 17, 18),
    Seq(19, 20, 21),
    Seq(22, 23, 24)
  )

  private val horizontalMills = Seq(
    Seq(1, 10, 22),
    Seq(3, 15, 24),
    Seq(4, 11, 19),
    Seq(6, 14, 21),
    Seq(7, 12, 16),
    Seq(9, 13, 18),
    Seq(2, 5, 8),
    Seq(17, 20, 23)
  )
  override def isMill(number: Int, field: Field): Boolean = {
    isMill(number, field, verticalMills) || isMill(
      number,
      field,
      horizontalMills
    )
  }
  private def isMill(
      number: Int,
      field: Field,
      millset: Seq[Seq[Int]]
  ): Boolean = {
    val millFields = millset.filter(_.contains(number)).head
    val otherFields = millFields.filter(_ != number)
    field.fields(number) == field.fields(millFields(0)) && field.fields(
      number
    ) == field.fields(
      millFields(2)
    ) &&
    field.fields(otherFields(0)) == field.fields(otherFields(1))
  }
}

class Mill(
    private val field: Field,
    private val millChecker: MillChecker
) {
  def isMill(number: Int): Boolean = {
    millChecker.isMill(number, field)
  }

  def existsMill(): Boolean = {
    val numberStream = Stream
      .from(1)
      .take(24)
      .filter(number => field.fields(number) != Stone.Empty)
    numberStream.exists(number => isMill(number))
  }
}
