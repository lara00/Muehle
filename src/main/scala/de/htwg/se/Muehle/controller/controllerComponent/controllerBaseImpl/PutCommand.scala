package de.htwg.se.Muehle
package controller.controllerComponent.controllerBaseImpl
import util.{Command, Event, Observable}
import model.{MillList, MillEvents}
import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap

class PutCommand(move: Move) extends Command[IGameStap] {
  override def noStep(t: IGameStap): IGameStap = move.gamefield

  override def doStep(t: IGameStap): IGameStap = {
    val game = move.playerstrategy.makeMove(move.gamefield, move.to, move.from)
    game(0)
  }

  override def undoStep(t: IGameStap): IGameStap = {
        GameStap(
          move.gamefield.gfield
            .deleteStone(move.to, move.gamefield.playername)
            .setStone(move.from, move.gamefield.playername),
          move.gamefield.gplayer,
          move.gamefield.gplayerlist
        )
  }

  override def redoStep(t: IGameStap): IGameStap = {
        GameStap(move.gamefield.gfield
            .deleteStone(move.to, move.gamefield.playername),
          move.gamefield.gplayer,
          move.gamefield.gplayerlist)
  }
}
