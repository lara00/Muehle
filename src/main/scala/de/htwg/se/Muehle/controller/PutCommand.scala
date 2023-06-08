package de.htwg.se.Muehle
package controller

import util.{Command, Event, Observable}
import model.{GameStap, PlayerStrategy, MillList, PlayerList, MillEvents}
import de.htwg.se.Muehle.model.MoveEvents

  /*Syle and AIplayer Missing*/
class PutCommand(move: Move) extends Command[GameStap] {
  var eventList: List[MoveEvents | MillEvents] = Nil
  var eventListRedo: List[MoveEvents | MillEvents] = Nil
  var mill: List[Int] = Nil
  override def noStep(t: GameStap): GameStap = move.gamefield

  override def doStep(t: GameStap): GameStap = {
    val game = move.playerstrategy.makeMove(move.gamefield, move.to, move.from)
    eventList = game(1) :: eventList
    game(0)
  }

  override def undoStep(t: GameStap): GameStap = {
    val updatedList = eventList.tail
    val lastEvent = eventList.head
    move.event match
      case MoveEvents.SetStone =>
        eventListRedo = MoveEvents.SetStone :: eventListRedo
        GameStap(
          move.gamefield.field
            .deleteStone(move.to, move.gamefield.player.name)
            .setStone(move.from, move.gamefield.player.name),
          move.gamefield.player,
          move.gamefield.playerlist
        )
      case MoveEvents.MoveStone =>
        eventListRedo = MoveEvents.MoveStone :: eventListRedo
         move.playerstrategy.makeMove(move.gamefield, move.to, move.from)(0)
      case MillEvents.DeleteStone => 
        eventList.head
        eventList.head match
          case MoveEvents.SetStone_Mill =>
            eventListRedo = MoveEvents.SetStone_Mill :: eventListRedo
            val nextPlayer = move.gamefield.playerlist.getNextPlayer(
            move.gamefield.player.incrementStoneintheField
            )
            val newField = move.gamefield.field
            .setStone(MillList.list.head, nextPlayer.name)
            .deleteStone(move.to, move.gamefield.player.name)
            mill = MillList.list.head :: mill
            MillList.deleteElement()
            val newPlayer = move.gamefield.player.decrementStoneintheField
            val newPlayerList = PlayerList(newPlayer, nextPlayer)
            GameStap(newField, newPlayer, newPlayerList)
          case MoveEvents.MoveStone_Mill => 
            eventListRedo = MoveEvents.MoveStone_Mill :: eventListRedo
            val nextPlayer = move.gamefield.playerlist.getNextPlayer(
            move.gamefield.player
            )
            val newField = move.gamefield.field
            .setStone(MillList.list.head, nextPlayer.name)
            .deleteStone(move.to, move.gamefield.player.name)
            mill = MillList.list.head :: mill
            MillList.deleteElement()
            val newPlayer = move.gamefield.player.decrementStoneintheField
            val newPlayerList = PlayerList(newPlayer, nextPlayer)
            GameStap(newField, newPlayer, newPlayerList)
          case _ => move.gamefield
      case _ => move.gamefield
  }

  override def redoStep(t: GameStap): GameStap = {
    val updatedList = eventList.tail
    val lastEvent = eventList.head
    eventList = lastEvent :: eventList
    move.event match
      case MoveEvents.SetStone =>
        GameStap(move.gamefield.field
            .deleteStone(move.to, move.gamefield.player.name),
          move.gamefield.player,
          move.gamefield.playerlist)
      case MoveEvents.MoveStone =>
        GameStap(
          move.gamefield.field
            .deleteStone(move.to, move.gamefield.player.name)
            .setStone(move.from, move.gamefield.player.name),
          move.gamefield.player,
          move.gamefield.playerlist
        )
      case MillEvents.DeleteStone => 
        val value = mill.head
        val input = new java.io.StringReader(value.toString() + "\n")
        var game = move.gamefield
        Console.withIn(input) {
          game = move.playerstrategy.makeMove(move.gamefield, move.to, move.from)(0)
        }
        mill.tail
        mill
        game
      case _ => move.gamefield 
  }
}
