package de.htwg.se.Muehle
package controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Muehle.model.gameComponent.IGameStap
import model.{MillEvents, MoveEvents}
import util.{Command, Event, Observable}

class PutCommand(move: Move) extends Command[IGameStap] {
  override def noStep(t: IGameStap): IGameStap = move.gamefield

  override def doStep(t: IGameStap): IGameStap = {
    val game = move.playerstrategy.makeMove(move.gamefield, move.to, move.from)
    game(0)
  }

  override def undoStep(t: IGameStap): IGameStap = {
        t.newGamestap(
          move.gamefield.gfield
            .deleteStone(move.to, move.gamefield.playername)
            .setStone(move.from, move.gamefield.playername),
          move.gamefield.gplayer,
          move.gamefield.gplayerlist
        )
  }

  override def redoStep(t: IGameStap): IGameStap = {
        t.newGamestap(move.gamefield.gfield
            .deleteStone(move.to, move.gamefield.playername),
          move.gamefield.gplayer,
          move.gamefield.gplayerlist)
  }
}
