package de.htwg.se.Muehle.model.playerstrategyComponent

import scala.util.Random
import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, Guice, Inject, Injector, Provides}
import com.google.inject.Key

import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl.HumanPlayer
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.AIPlayer

trait IPlayerStrategy extends AbstractModule:
  final def makeMove(gameStap: IGameStap, to: Int, from: Int): (IGameStap, MoveEvents) = handleResult(gameStap, handleMove(gameStap, to, from))
  protected def handleMove(gameStap: IGameStap, to: Int, from: Int): (IGameStap, MoveEvents)
  protected def handleResult(gameStap: IGameStap, gamefield: (IGameStap, MoveEvents)): (IGameStap, MoveEvents)

object IGameInjector:
  def createInjector(): Injector = Guice.createInjector(new AbstractModule() {
      @Provides @Named("AIPlayer") def provideAIPlayer: IPlayerStrategy = new AIPlayer()
      @Provides @Named("HumanPlayer") def provideHumanPlayer: IPlayerStrategy = new HumanPlayer()
    })
