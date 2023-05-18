package de.htwg.se.Muehle
package model

// Chain of Responsibility Design Pattern
object Gamemove {
  def chainOfResponsibility(
      gameStap: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    val h1: GameHandler = new WrongValue()
    val h2: GameHandler = new SetaStonewithoutMill()
    val h3: GameHandler = new SetaStonewithMill()
    val h4: GameHandler = new SwitchorJumpaStonewithoutMill()
    val h5: GameHandler = new SwitchorJumpaStonewithMill()

    h1.setSuccessor(h2)
    h2.setSuccessor(h3)
    h3.setSuccessor(h4)
    h4.setSuccessor(h5)

    h1.handleRequest(gameStap, to, from)
  }
}
abstract class GameHandler() {
  protected var successor: GameHandler = _
  def setSuccessor(successor: GameHandler): Unit = {
    this.successor = successor
  }
  def handleRequest(aktivGame: GameStap, to: Int, from: Int): GameStap
  protected def passRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    successor.handleRequest(aktivGame, to, from)
  }
}
class WrongValue() extends GameHandler() {
  override def handleRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    if (
      StoneMovement(
        aktivGame.player,
        aktivGame.field,
        to,
        from
      ) == aktivGame.field
    ) {
      println("This move is not possible try again")
      aktivGame
    } else {
      passRequest(aktivGame, to, from)
    }
  }
}
class SetaStonewithoutMill() extends GameHandler() {
  override def handleRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    if (
      aktivGame.player.stonetoput != 0 && Mill(
        newfield,
        VerticalAndHorizontalMillChecker
      ).isMill(to) == false
    ) {
      val newPlayer = aktivGame.playerlist.getNextPlayer(aktivGame.player)
      val playerlist =
        aktivGame.playerlist.updateStonesInField(aktivGame.player)
      GameStap(newfield, newPlayer, playerlist)
    } else {
      passRequest(
        aktivGame,
        to,
        from
      )
    }
  }
}
class SetaStonewithMill() extends GameHandler() {
  override def handleRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    if (
      aktivGame.player.stonetoput != 0 && Mill(
        newfield,
        VerticalAndHorizontalMillChecker
      ).isMill(to) == true
    ) {
      val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
      val playerlist =
        aktivGame.playerlist.updateStonesInField(aktivGame.player)
      GameStap(
        newfield,
        aktivGame.player.incrementStoneintheField.stonetoputinthefield,
        playerlist
      )
    } else {
      passRequest(
        aktivGame,
        to,
        from
      )
    }
  }
}
class SwitchorJumpaStonewithoutMill() extends GameHandler() {
  override def handleRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    if (Mill(newfield, VerticalAndHorizontalMillChecker).isMill(to) == false) {
      GameStap(
        newfield,
        aktivGame.playerlist.getNextPlayer(aktivGame.player),
        aktivGame.playerlist
      )
    } else {
      passRequest(aktivGame, to, from)
    }
  }
}
class SwitchorJumpaStonewithMill() extends GameHandler() {
  override def handleRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    GameStap(
      newfield,
      aktivGame.player,
      aktivGame.playerlist
    )
  }
}
