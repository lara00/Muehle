package de.htwg.se.Muehle.model

case class GameStap(field: Field, player: Player, playerlist: PlayerList):
  def getNextPlayer: Player = playerlist.getNextPlayer(player)
  // def updateStonesInField: PlayerList = playerlist.updateStonesInField(player)
