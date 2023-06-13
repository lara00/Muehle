package de.htwg.se.Muehle
package model

import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.*
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap

class Gamefield(val gamesetting: IGameStap, val gamestrategy: IPlayerStrategy)

class GamefieldBuilder():
  private var stonetoput: IGameStap = GameStap(Field(), PlayerList(9).getFirstPlayer, PlayerList(9))
  private var singlegamer: IPlayerStrategy = HumanPlayer()

  def addStonesToPut(quantity: Int): GamefieldBuilder =
    stonetoput = GameStap(Field(), PlayerList(quantity).getFirstPlayer, PlayerList(quantity))
    this

  def addSingleGamer(singelegamer: Boolean): GamefieldBuilder =
    singelegamer match
      case true  => singlegamer = AIPlayer(); this
      case false => singlegamer = HumanPlayer(); this

  def build(): Gamefield =
    new Gamefield(stonetoput, singlegamer)
