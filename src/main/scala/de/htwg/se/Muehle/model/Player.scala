package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.model.Field
import de.htwg.se.Muehle.model.Stone

case class Player(
    name: Stone,
    stonetoput: Int,
    stoneintheField: Int
) {

  override def toString(): String = {
    s"Player($name, $stonetoput, $stoneintheField)"
  }
  def stonetoputinthefield: Player = {
    if (stonetoput > 0) {
      this.copy(stonetoput = stonetoput - 1)
    } else {
      throw new RuntimeException(
        "Cannot put more stones in the field, stonetoput value cannot be negative!"
      )
    }
  }
  def incrementStoneintheField: Player =
    this.copy(stoneintheField = stoneintheField + 1)
  def decrementStoneintheField: Player =
    this.copy(stoneintheField = stoneintheField - 1)
  def setstone(
      field: Field,
      aktiveplayer: Player,
      fieldenummber: Int
  ): Field =
    field.setStone(fieldenummber, aktiveplayer.name)
  def statusplayer(): Int = {
    stonetoput match {
      case n if n > 0 =>
        println("Placing a stone on the field")
        0
      case n if stoneintheField > 3 =>
        println("Moving a stone on the field")
        1
      case _ =>
        println("Jumping with a stone on the field")
        2
    }
  }
}
