package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.model.Field
import de.htwg.se.Muehle.model.Stone

case class Player(
    name: Stone,
    playedStone: Int,
    stoneintheField: Int
) {

  override def toString(): String = {
    s"Player($name, $playedStone, $stoneintheField)"
  }
  def incrementPlayedStone: Player = this.copy(playedStone = playedStone + 1)
  def incrementStoneintheField: Player =
    this.copy(stoneintheField = stoneintheField + 1)
  def decrementStoneintheField: Player =
    this.copy(stoneintheField = stoneintheField - 1)
}
def setstone(
    field: Field,
    aktiveplayer: Player,
    fieldenummber: Int
): Field =
  field.setStone(fieldenummber, aktiveplayer.name)

def changePlayer(aktiveplayer: Player, player: List[Player]): Player =
  if (aktiveplayer == player(0)) player(1) else player(0)
