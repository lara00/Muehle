package de.htwg.se.Muehle.model.gameComponent

import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.{Stone, MoveEvents, MillEvents, PlayerList}

trait IGameStap {
  def newGamestap(field: IField, player: IPlayer, playerlist: PlayerList): IGameStap
  def gplayer: IPlayer
  def gplayerlist: PlayerList
  def playername: Stone
  def gfield: IField
  def stonesofaktiveplayer: Int
  def getNextPlayer: IPlayer
  def timetoSetMoveJumporMill(to: Int, from: Int): (IGameStap, MoveEvents)
  def handleMill(delete: Int): (IGameStap, MillEvents)
}