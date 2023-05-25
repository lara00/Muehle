package de.htwg.se.Muehle.model
import scala.util.{Try}

val minField = 1
val maxField = 24

case class Field(
    fields: Map[Int, Stone] = (1 to 24).map(_ -> Stone.Empty).toMap
) {
  private val maxStoneLength = 5
  def size: Int = fields.size
  def setStone(number: Int, value: Stone): Field = {
    fields(number) match {
      case Stone.Empty => copy(fields = fields.updated(number, value))
      case Stone.Black | Stone.White => this
    }
  }
  def deleteStone(number: Int, value: Stone): Field = {
    fields(number) match {
      case `value` =>
        copy(fields = fields.updated(number, Stone.Empty))
      case _ => this
    }
  }
  def movestone(from: Int, to: Int, value: Stone): Field = {
    if (fields(from) == value && fields(to) == Stone.Empty) {
      val field = deleteStone(from, value)
      field.setStone(to, value)
    } else {
      this
    }
  }
  def stoneString(number: Int): String = fields(number).toString
  def formatRow(fields: String*): String =
    fields.map(_.padTo(maxStoneLength, ' ')).mkString(" | ") + "\n"
  def isFieldValid(intValueString: String): Boolean =
    Try(intValueString.toInt).toOption.exists(intValue =>
      (minField to maxField).contains(intValue)
    )

  private def rowFormat(fields: String*): String = formatRow(fields: _*)

  override def toString(): String =
    rowFormat(
      stoneString(1),
      "",
      "",
      stoneString(2),
      "",
      "",
      stoneString(3)
    ) +
      rowFormat(
        "",
        stoneString(4),
        "",
        stoneString(5),
        "",
        stoneString(6),
        ""
      ) +
      rowFormat(
        "",
        "",
        stoneString(7),
        stoneString(8),
        stoneString(9),
        "",
        ""
      ) +
      rowFormat(
        stoneString(10),
        stoneString(11),
        stoneString(12),
        "",
        stoneString(13),
        stoneString(14),
        stoneString(15)
      ) +
      rowFormat(
        "",
        "",
        stoneString(16),
        stoneString(17),
        stoneString(18),
        "",
        ""
      ) +
      rowFormat(
        "",
        stoneString(19),
        "",
        stoneString(20),
        "",
        stoneString(21),
        ""
      ) +
      rowFormat(
        stoneString(22),
        "",
        "",
        stoneString(23),
        "",
        "",
        stoneString(24)
      )
}
