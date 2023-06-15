package de.htwg.se.Muehle


import java.util.AbstractMap
import com.google.inject.AbstractModule
import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, Guice, Inject, Injector, Provides}
import com.google.inject.Key
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.spi.Message
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.controller.controllerComponent.IController
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.fieldComponent.fieldImpl.Field
import de.htwg.se.Muehle.model.playerComponent.playerImpl.Player
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl.HumanPlayer


class Module extends AbstractModule with ScalaModule {
  val defaultSize: Int = 4

  override def configure(): Unit = {
    bind[IField].toInstance(Field())
    bind[IPlayer].toInstance(Player(Stone.White, defaultSize, 0))
    bind[PlayerList].toInstance(PlayerList(defaultSize))
    bind[IPlayerStrategy].to[HumanPlayer] 
    bind[IGameStap].toInstance(GameStap(Field(), Player(Stone.White, defaultSize, 0), PlayerList(defaultSize)))
    bind[IController].toInstance(Controller(GameStap(Field(), Player(Stone.White, defaultSize, 0), PlayerList(defaultSize)), HumanPlayer()))
  }
}
