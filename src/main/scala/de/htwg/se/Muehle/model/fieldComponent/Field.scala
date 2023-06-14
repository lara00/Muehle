package de.htwg.se.Muehle.model.fieldComponent

import scala.util.{Try}
import de.htwg.se.Muehle.model.Stone
import com.google.inject.Inject

case class Field(fields: Map[Int, Stone] = (1 to 24).map(_ -> Stone.Empty).toMap):
  def size: Int = fields.size

  private def performAction(number: Int, value: Stone)(check: Stone => Boolean)(action: (Field, Stone) => Field): Field =
    fields.get(number).filter(check).map(stone => action(this, stone)).getOrElse(this)

  def setStone(number: Int, value: Stone): Field = performAction(number, value)(_ == Stone.Empty) { (field, _) => field.copy(fields = field.fields.updated(number, value))}

  def deleteStone(number: Int, value: Stone): Field = performAction(number, value)(_ == value) { (field, _) => field.copy(fields = field.fields.updated(number, Stone.Empty))}

  def movestone(from: Int, to: Int, value: Stone): Field =
    performAction(from, value)(_ == value && fields(to) == Stone.Empty) {
      (field, stone) =>
        val updatedField = field.copy(fields = field.fields.updated(from, Stone.Empty))
        updatedField.copy(fields = updatedField.fields.updated(to, stone))}

  def stoneString(number: Int): String = fields(number).toString

  def isFieldValid(intValueString: String): Boolean = Try(intValueString.toInt).toOption.exists(intValue => (1 to 24).contains(intValue))

  def getStonePositions(stone: Stone): List[Int] = fields.filter { case (_, s) => s == stone }.keys.toList

  def getWhiteStonePositions: List[Int] = getStonePositions(Stone.White)

  def getBlackStonePositions: List[Int] = getStonePositions(Stone.Black)

  private def formatRow(fields: String*): String = fields.map(_.padTo(5, ' ')).mkString(" | ") + "\n"

  override def toString(): String =
    formatRow(stoneString(1), "", "", stoneString(2), "", "", stoneString(3)) +
    formatRow("",stoneString(4),"",stoneString(5),"",stoneString(6),"") +
    formatRow("","",stoneString(7),stoneString(8),stoneString(9),"","") +
    formatRow(stoneString(10),stoneString(11),stoneString(12),"",stoneString(13), stoneString(14),stoneString(15)) +
    formatRow("","",stoneString(16),stoneString(17),stoneString(18),"", "") +
    formatRow("",stoneString(19),"",stoneString(20),"",stoneString(21),"") +
    formatRow(stoneString(22),"","",stoneString(23),"","",stoneString(24))