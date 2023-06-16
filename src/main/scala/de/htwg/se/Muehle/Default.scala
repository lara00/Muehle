package de.htwg.se.Muehle

import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.controller.controllerComponent.IController
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.fieldComponent.fieldImpl.Field
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl.HumanPlayer
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.playerComponent.playerImpl.Player
import de.htwg.se.Muehle.model.Stone

object Default:
  given IGameStap = GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4))
  given IController = Controller()
  given IPlayerStrategy = HumanPlayer()
  given IField = Field()
  given IPlayer = Player(Stone.White, 4,0)