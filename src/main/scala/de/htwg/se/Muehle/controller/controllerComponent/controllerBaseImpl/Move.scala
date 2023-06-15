package de.htwg.se.Muehle
package controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import model.{MoveEvents, MillEvents}

case class Move(event : MoveEvents | MillEvents ,gamefield: IGameStap, playerstrategy: IPlayerStrategy, to: Int,from: Int)
