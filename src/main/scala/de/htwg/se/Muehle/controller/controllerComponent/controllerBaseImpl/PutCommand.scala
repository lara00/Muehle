package de.htwg.se.Muehle
package controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Muehle.model.gameComponent.IGameStap
import model.{MillEvents, MoveEvents}
import util.{Command, Event, Observable}
import de.htwg.se.Muehle.model.PlayerList

class PutCommand(move: Move) extends Command[IGameStap] {

  override def doStep(t: IGameStap): IGameStap = {
    move.event match
      case MillEvents.DeleteStone | MillEvents.EndGame | MillEvents.WrongDelete =>
        move.gamefield.handleMill(move.to)(0)
      case MoveEvents.MoveStone | MoveEvents.SetStone_Mill | MoveEvents.SetStone | MoveEvents.MoveStone_Mill | MoveEvents.NoMove =>
        move.playerstrategy.makeMove(move.gamefield, move.to, move.from)(0)
  }

  override def undoStep(t: IGameStap): IGameStap = {
    move.event match
      case MoveEvents.MoveStone | MoveEvents.MoveStone_Mill =>
         t.newGamestap(move.gamefield.gfield.deleteStone(move.from, move.gamefield.gplayer.pname).setStone(move.to, move.gamefield.gplayer.pname),
          move.gamefield.gplayer,
          move.gamefield.gplayerlist)
      case MoveEvents.SetStone | MoveEvents.SetStone_Mill => 
        t.newGamestap(move.gamefield.gfield.deleteStone(move.to, move.gamefield.playername),
          move.gamefield.gplayer,
          move.gamefield.gplayerlist)
      case MoveEvents.NoMove | MillEvents.EndGame | MillEvents.WrongDelete   => t
      case MillEvents.DeleteStone => 
        val nextPlayer = move.gamefield.gplayerlist.getNextPlayer(
        move.gamefield.gplayer).incrementStoneintheField
        val newField = move.gamefield.gfield
          .setStone(move.to, move.gamefield.gplayer.pname)
        val newPlayerList = PlayerList(move.gamefield.gplayer, nextPlayer)
        t.newGamestap(newField, nextPlayer, newPlayerList)
  }
  override def redoStep(t: IGameStap): IGameStap = {
    move.event match
      case MillEvents.DeleteStone | MillEvents.EndGame | MillEvents.WrongDelete =>
        move.gamefield.handleMill(move.to)(0)
      case MoveEvents.MoveStone | MoveEvents.SetStone_Mill | MoveEvents.SetStone | MoveEvents.MoveStone_Mill | MoveEvents.NoMove =>
        move.playerstrategy.makeMove(move.gamefield, move.to, move.from)(0)
  }
}
