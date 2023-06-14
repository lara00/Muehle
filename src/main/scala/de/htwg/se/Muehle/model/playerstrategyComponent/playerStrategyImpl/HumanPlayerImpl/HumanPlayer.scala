package de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl

import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy


class HumanPlayer extends IPlayerStrategy:
  protected def handleMove(gameStap: IGameStap, to: Int, from: Int): (IGameStap, MoveEvents) = gameStap.timetoSetMoveJumporMill(to, from)
  protected def handleResult(gameStap: IGameStap, gamefield: (IGameStap, MoveEvents)): (IGameStap, MoveEvents) = gamefield