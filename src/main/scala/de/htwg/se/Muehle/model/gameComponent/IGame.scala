package de.htwg.se.Muehle.model.gameComponent

import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.{Stone, MoveEvents, MillEvents}

trait IGameStap {
  def gplayer: Player
  def gplayerlist: PlayerList
  def playername: Stone
  def gfield: Field
  def stonesofaktiveplayer: Int
  def getNextPlayer: Player
  def timetoSetMoveJumporMill(to: Int, from: Int): (IGameStap, MoveEvents)
  def handleMill(delete: Int): (IGameStap, MillEvents)
}
