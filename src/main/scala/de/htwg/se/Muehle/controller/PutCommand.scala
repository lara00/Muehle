package de.htwg.se.Muehle
package controller

import util.{Command, Event, Observable}
import model.{GameStap, PlayerStrategy, MillList, PlayerList, MillEvents}
import de.htwg.se.Muehle.model.MoveEvents

class PutCommand(move: Move) extends Command[GameStap] {
  override def noStep(t: GameStap): GameStap = move.gamefield

  override def doStep(t: GameStap): GameStap = {
    val game = move.playerstrategy.makeMove(move.gamefield, move.to, move.from)
    game(0)
  }

  override def undoStep(t: GameStap): GameStap = {
        GameStap(
          move.gamefield.field
            .deleteStone(move.to, move.gamefield.player.name)
            .setStone(move.from, move.gamefield.player.name),
          move.gamefield.player,
          move.gamefield.playerlist
        )
  }

  override def redoStep(t: GameStap): GameStap = {
        GameStap(move.gamefield.field
            .deleteStone(move.to, move.gamefield.player.name),
          move.gamefield.player,
          move.gamefield.playerlist)
  }
}
