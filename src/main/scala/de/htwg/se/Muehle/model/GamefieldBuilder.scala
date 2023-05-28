package de.htwg.se.Muehle
package model

class Gamefield(
    val gamesetting: GameStap,
    val gamestrategy: PlayerStrategy
) {
  override def toString: String = {
    s"Gamefield(playerlist=${gamesetting.playerlist}, singlegamer=${gamestrategy.getClass.getSimpleName})"
  }
}
class GamefieldBuilder {
  private var stonetoput: GameStap =
    GameStap(Field(), PlayerList(9).getFirstPlayer, PlayerList(9))

  private var singlegamer: PlayerStrategy = HumanPlayer()

  def addStonesToPut(quantity: Int): GamefieldBuilder = {
    this.stonetoput = GameStap(
      Field(),
      PlayerList(quantity).getFirstPlayer,
      PlayerList(quantity)
    )
    this
  }

  def addSingleGamer(singelegamer: Boolean): GamefieldBuilder = {
    singelegamer match
      case true  => this.singlegamer = AIPlayer()
      case false => this.singlegamer = HumanPlayer()
    this
  }

  def build(): Gamefield = {
    new Gamefield(stonetoput, singlegamer)
  }
}
