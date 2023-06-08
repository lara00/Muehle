package de.htwg.se.Muehle
package controller

import model.{GameStap, PlayerStrategy, MoveEvents, MillEvents}

case class Move(event : MoveEvents | MillEvents ,gamefield:
     GameStap, playerstrategy: PlayerStrategy, to: Int,from: Int)
