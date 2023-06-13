package de.htwg.se.Muehle
package controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import model.{MoveEvents, MillEvents}
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.PlayerList

case class Move(event : MoveEvents | MillEvents ,gamefield:
     IGameStap, playerstrategy: IPlayerStrategy, to: Int,from: Int)
