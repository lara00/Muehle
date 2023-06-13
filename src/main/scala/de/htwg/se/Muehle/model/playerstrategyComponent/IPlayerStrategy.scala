package de.htwg.se.Muehle.model.playerstrategyComponent

import scala.util.Random
import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.gameComponent.IGameStap

trait IPlayerStrategy:
  final def makeMove(gameStap: IGameStap, to: Int, from: Int): (IGameStap, MoveEvents) = handleResult(gameStap, handleMove(gameStap, to, from))
  protected def handleMove(gameStap: IGameStap, to: Int, from: Int): (IGameStap, MoveEvents)
  protected def handleResult(gameStap: IGameStap, gamefield: (IGameStap, MoveEvents)): (IGameStap, MoveEvents)
