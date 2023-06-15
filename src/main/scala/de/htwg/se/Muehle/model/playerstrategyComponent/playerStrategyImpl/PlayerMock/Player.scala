package de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.PlayerMock

import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.MoveEvents

class MockPlayer extends IPlayerStrategy:
  protected def handleMove(gameStap: IGameStap, to: Int, from: Int): (IGameStap, MoveEvents) = gameStap.timetoSetMoveJumporMill(to, from)
  protected def handleResult(gameStap: IGameStap, gamefield: (IGameStap, MoveEvents)): (IGameStap, MoveEvents) = gamefield