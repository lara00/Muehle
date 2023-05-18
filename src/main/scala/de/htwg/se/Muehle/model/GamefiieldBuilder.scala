package de.htwg.se.Muehle
package model

/*Builder*/
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
    val playerlist = PlayerList(9)
    GameStap(Field(), playerlist.getFirstPlayer, playerlist)
  private var singlegamer: PlayerStrategy = HumanPlayer()
  def addStonesToPut(quantity: Int): GamefieldBuilder = {
    val newList = PlayerList(quantity)
    this.stonetoput = GameStap(Field(), newList.getFirstPlayer, newList)
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
