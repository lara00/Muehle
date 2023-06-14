/*
package de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.PlayerMock

import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.PlayerImpl.AIPlayer
import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy

class MockAIPlayer extends AIPlayer with IPlayerStrategy {

  private def handleStoneintheField(gamefield: IGameStap): (IGameStap, MoveEvents) = {
    val (move0, move1) = generate_move(gamefield)
    millcheck(move1,move0, gamefield)
  }

  private def findValidRandomMove(gamefield: IGameStap): Int = 1

  private def field_is_Free(field: Field, stone: Stone): Int = 1
  private def generate_move(gamefield: IGameStap): (Int, Int) = {(0, 0)}

  override def generateRandomNumber: Int = 2

  override def millcheck(to:Int, from: Int, gamefield: IGameStap) = gamefield.timetoSetMoveJumporMill(to, from)
}
*/

