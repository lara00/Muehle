package de.htwg.se.Muehle
package model

import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Guice

case class PlayerList @Inject()(players: List[IPlayer]):
  def getFirstPlayer: IPlayer = players.head

  def getNextPlayer(aktivePlayer: IPlayer): IPlayer = players.find(_ != aktivePlayer).get

  def allowedtodeleteastone(aktivePlayer: IPlayer): Boolean =
    val nextplayer = getNextPlayer(aktivePlayer)
    (nextplayer.pstoneinField != 3 || nextplayer.pstonetoput != 0) &&
    (nextplayer.pstoneinField + nextplayer.pstonetoput != 3)

  def threeStonesontheField(player: IPlayer): Boolean =
    val nextplayer = getNextPlayer(player)
    nextplayer.pstoneinField == 3 || (nextplayer.pstoneinField + nextplayer.pstonetoput == 3)

  def updateStones(player: IPlayer, increment: Boolean): PlayerList = {
    PlayerList(players.map { p =>
      p match
        case `player` if increment =>
          p.stonetoputinthefield.incrementStoneintheField
        case `player` => p.decrementStoneintheField
        case _        => p
    })
  }

  def updateStonesInField(aktivePlayer: IPlayer): PlayerList = updateStones(aktivePlayer, increment = true)

  def updateStonesafterMill(aktivePlayer: IPlayer): PlayerList = updateStones(aktivePlayer, increment = false)

  def printStonesToSet(): String =
    players.map { player => val playedStones = List.fill(player.pstonetoput)(if (player.pname == Stone.White) "W" else "B").mkString(" ")
        s"${player.pname} Player: Stone to set: $playedStones"}.mkString("\n")

object PlayerList:
  def apply(input: Int): PlayerList = given_IPlayer.pplayerList(input)
  
  def apply(player1: IPlayer, player2: IPlayer): PlayerList = PlayerList(List(player1, player2))
