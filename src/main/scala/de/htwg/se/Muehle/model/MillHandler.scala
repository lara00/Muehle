package de.htwg.se.Muehle.model

import scala.io.StdIn.readLine

object MillList {
  private var mill: List[Int] = Nil

  def list: List[Int] = mill
  def deleteElement(): Unit = mill = mill.tail
  def add_elementint(element: Int): Unit = mill = element :: mill
}

case class MillHandler(aktivGame: GameStap) {
  def funktion(): GameStap =
    println(aktivGame.field.toString())
    println("Mill delete a Stone")
    val delete = readLine().toInt
    MillList.add_elementint(delete)
    aktivGame match {
      case GameStap(field, player, playerlist)
          if player.name != field.fields(delete)
            && !Mill(field).isMill(delete)
            && (playerlist.allowedtodeleteastone(player)) =>
        updateGameStapAfterDeleteStone(aktivGame, delete)
      case GameStap(_, player, playerlist)
          if playerlist.threeStonesontheField(player) =>
        println("${player.name} winns")
        updateGameStapAfterDeleteStone(aktivGame, delete)
      case _ =>
        println("This stone is not possible to delete")
        if (Mill(aktivGame.field).isMill(delete)) {
          println(
            "This stone is not possible to delete, because is part of a Mill"
          )
        }
        MillList.deleteElement()
        funktion()
    }

  private def updateGameStapAfterDeleteStone(
      gameStap: GameStap,
      delete: Int
  ): GameStap = {
    val newPlayer = gameStap.playerlist.getNextPlayer(gameStap.player)
    val newfield = gameStap.field.deleteStone(delete, newPlayer.name)
    val newPlayerlist = gameStap.playerlist.updateStonesafterMill(newPlayer)
    GameStap(newfield, newPlayer.decrementStoneintheField, newPlayerlist)
  }
}
