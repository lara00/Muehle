package de.htwg.se.Muehle.model

var currentIndex = 0;

case class PlayerList(players: List[Player]) {
  def getFirstPlayer: Player = players.head
  def getNextPlayer(aktivePlayer: Player): Player =
    players.find(_ != aktivePlayer).get
  def allowedtodeleteastone(aktivePlayer: Player): Boolean =
    val nextplayer = players.find(_ != aktivePlayer).get
    nextplayer.stoneintheField != 3 || nextplayer.stonetoput != 0
  def threeStonesontheField(player: Player): Boolean =
    players.find(_ != player).get.stoneintheField == 3
  def updateStonesInField(aktivePlayer: Player): PlayerList = {
    val updatedPlayers = players.map { p =>
      if (p == aktivePlayer)
        p.stonetoputinthefield.incrementStoneintheField
      else p
    }
    PlayerList(updatedPlayers)
  }
  def updateStonesafterMill(aktivePlayer: Player): PlayerList = {
    val updatedPlayers = players.map { p =>
      if (p == aktivePlayer)
        p.decrementStoneintheField
      else p
    }
    PlayerList(updatedPlayers)
  }
  def printStonesToSet(): String = {
    val output = new StringBuilder()
    players.foreach { player =>
      val playedStones = List
        .fill(player.stonetoput)(if (player.name == Stone.White) "W" else "B")
        .mkString(" ")
      output.append(s"${player.name} Player: Stone to set: $playedStones\n")
    }
    output.toString
  }
}

object PlayerList {
  def apply(input: Int): PlayerList = {
    val players = List(
      new Player(Stone.White, input, 0),
      new Player(Stone.Black, input, 0)
    )
    PlayerList(players)
  }
}
