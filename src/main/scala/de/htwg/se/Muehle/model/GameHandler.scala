package de.htwg.se.Muehle
package model

object GameHandlerQuee {
  def chainOfResponsibility(
      gameStap: GameStap,
      to: Int,
      from: Int
  ): GameStap = {
    val h2: GameHandler = new SetaStonewithoutMill()
    val h3: GameHandler = new SetaStonewithMill()
    val h4: GameHandler = new SwitchorJumpaStonewithoutMill()
    val h5: GameHandler = new SwitchorJumpaStonewithMill()

    h2.setSuccessor(h3)
    h3.setSuccessor(h4)
    h4.setSuccessor(h5)
    h2.handleRequest(gameStap, to, from).getOrElse(gameStap)
  }
}
abstract class GameHandler() {
  protected var successor: GameHandler = _
  def setSuccessor(successor: GameHandler): Unit = {
    this.successor = successor
  }

  def handleRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): Option[GameStap]

  protected def passRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): Option[GameStap] = {
    successor.handleRequest(aktivGame, to, from)
  }
}
class SetaStonewithoutMill() extends GameHandler() {
  override def handleRequest(
      aktivGame: GameStap,
      to: Int,
      from: Int
  ): Option[GameStap] = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    if (
      aktivGame.player.stonetoput != 0 && Mill(
        newfield
      ).isMill(to) == false && newfield != aktivGame.field
    ) {
      val newPlayer = aktivGame.playerlist.getNextPlayer(aktivGame.player)
      val playerlist =
        aktivGame.playerlist.updateStonesInField(aktivGame.player)
      Some(GameStap(newfield, newPlayer, playerlist))
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
  ): Option[GameStap] = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    if (
      aktivGame.player.stonetoput != 0 && Mill(
        newfield
      ).isMill(to) == true && newfield != aktivGame.field
    ) {
      val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
      val playerlist =
        aktivGame.playerlist.updateStonesInField(aktivGame.player)
      Some(
        MillHandler(
          GameStap(
            newfield,
            aktivGame.player.incrementStoneintheField.stonetoputinthefield,
            playerlist
          )
        ).funktion()
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
  ): Option[GameStap] = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    if (
      Mill(newfield).isMill(
        to
      ) == false && newfield != aktivGame.field && from != -1
    ) {
      Some(
        GameStap(
          newfield,
          aktivGame.playerlist.getNextPlayer(aktivGame.player),
          aktivGame.playerlist
        )
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
  ): Option[GameStap] = {
    val newfield = StoneMovement(aktivGame.player, aktivGame.field, to, from)
    if (newfield != aktivGame.field && from != -1) {
      Some(
        MillHandler(
          GameStap(
            newfield,
            aktivGame.player,
            aktivGame.playerlist
          )
        ).funktion()
      )
    } else {
      println("This move is not possible try again")
      None
    }

  }
}
