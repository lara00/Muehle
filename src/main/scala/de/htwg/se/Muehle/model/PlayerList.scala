package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.Stone

case class PlayerList(players: List[Player]):
  def getFirstPlayer: Player = players.head

  def getNextPlayer(aktivePlayer: Player): Player = players.find(_ != aktivePlayer).get

  def allowedtodeleteastone(aktivePlayer: Player): Boolean =
    val nextplayer = getNextPlayer(aktivePlayer)
    (nextplayer.stoneintheField != 3 || nextplayer.stonetoput != 0) &&
    (nextplayer.stoneintheField + nextplayer.stonetoput != 3)

  def threeStonesontheField(player: Player): Boolean =
    val nextplayer = getNextPlayer(player)
    nextplayer.stoneintheField == 3 || (nextplayer.stoneintheField + nextplayer.stonetoput == 3)

  def updateStones(player: Player, increment: Boolean): PlayerList = {
    PlayerList(players.map { p =>
      p match
        case `player` if increment =>
          p.stonetoputinthefield.incrementStoneintheField
        case `player` => p.decrementStoneintheField
        case _        => p
    })
  }

  def updateStonesInField(aktivePlayer: Player): PlayerList = updateStones(aktivePlayer, increment = true)

  def updateStonesafterMill(aktivePlayer: Player): PlayerList = updateStones(aktivePlayer, increment = false)

  def printStonesToSet(): String =
    players.map { player => val playedStones = List.fill(player.stonetoput)(if (player.name == Stone.White) "W" else "B").mkString(" ")
        s"${player.name} Player: Stone to set: $playedStones"}.mkString("\n")

object PlayerList:
  def apply(input: Int): PlayerList = PlayerList(List(new Player(Stone.White, input, 0), new Player(Stone.Black, input, 0)))
  
  def apply(player1: Player, player2: Player): PlayerList = PlayerList(List(player1, player2))
  
