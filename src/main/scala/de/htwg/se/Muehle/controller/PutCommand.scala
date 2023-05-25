package de.htwg.se.Muehle
package controller

import util.{Command, Event, Observable}
import model.{GameStap, PlayerStrategy}
import de.htwg.se.Muehle.controller.UndoRedoStepState

class PutCommand(move: Move) extends Command[GameStap] {
  var mill: List[Int] = Nil
  override def noStep(t: GameStap): GameStap = move.gamefield

  override def doStep(t: GameStap): GameStap =
    move.playerstrategy.makeMove(move.gamefield, move.to, move.from)

  override def undoStep(t: GameStap): GameStap = {
    val nextState = UndoRedoStepState(false, move).movemanager(move, mill)
    mill = nextState(1)
    nextState(0)
  }

  override def redoStep(t: GameStap): GameStap = {
    val nextState = UndoRedoStepState(true, move).movemanager(move, mill)
    mill = nextState(1)
    nextState(0)
  }
}
