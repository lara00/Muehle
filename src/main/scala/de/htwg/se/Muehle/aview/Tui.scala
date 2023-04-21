package de.htwg.se.Muehle
package aview

import scala.io.StdIn.readLine
import de.htwg.se.Muehle.model.{Field, Player, Stone, PlayerList}
import scala.annotation.meta.field
import scala.annotation.tailrec
import scala.util.{Try, Success, Failure}

class Tui {
  val invalidInputMsg =
    "Invalid input. Please enter a valid number between 1 and 24, or 'q' to quit"

  def processInput(input: String): Unit = {
    input match
      case s if s.matches("n[37]") => 
        val players = PlayerList(s.substring(1).toInt)
        loop(Field(), players.getFirstPlayer, players)
      case "end" =>
        println("End Game")
      @tailrec
      def loop(field: Field, activePlayer: Player, playerList: PlayerList): String = {
      println(inputText(activePlayer, playerList))
      readLine().toString() match {
      case "q" =>
        "End game"
      case intValueString if field.isFieldValid(intValueString) =>
        val intValue = intValueString.toInt
        activePlayer.statusplayer() match {
          case 0 =>
            val newField = playStone(intValue, activePlayer, field, playerList)
            if (newField != field)
              val nextplayer = playerList.getNextPlayer(activePlayer)
              val nextPlaxerlist = playerList.updateStonesInField(activePlayer)
              loop(newField, nextplayer, nextPlaxerlist) else loop(field, activePlayer, playerList)
          case 1 =>
            println("move()- Methode fehlt not implimented")
            "End game"
          case 2 =>
            println("move_with_jump() - Methode not implimented")
            "End game"
        }
      case other =>
        println(invalidInputMsg)
        loop(field, activePlayer, playerList)
    }
  }
  }

  def playStone(
      intValue: Int,
      activePlayer: Player,
      field: Field,
      playerList: PlayerList
  ): Field = {
    try {
      val newField = field.setStone(intValue, activePlayer.name)
      println(newField.printfeld())
      newField
    } catch {
      case e: IllegalArgumentException =>
        println(s"Mistack: ${e.getMessage}")
        println("Usa a new FieldV")
        field
    }
  }
  def inputText(activePlayer: Player, playerList: PlayerList): String = {
   s"Give the number or the field where you want to set a stone, ${activePlayer.name} or q (quit)" + "\n" + playerList.printStonesToSet()
  }
}
