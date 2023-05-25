package de.htwg.se.Muehle
package controller

import model.{GameStap, PlayerStrategy}

case class Move(
    gamefield: GameStap,
    playerstrategy: PlayerStrategy,
    to: Int,
    from: Int
)
