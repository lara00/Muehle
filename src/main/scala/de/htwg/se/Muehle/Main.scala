package de.htwg.se.Muehle

import de.htwg.se.Muehle.aview.Tui
import controller.Controller
import model.{Field, PlayerList, Stone, GameStap, HumanPlayer}

object Main {
  def main(args: Array[String]): Unit = {
    println("Play Muehle")
    val controller = Controller(
      GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4)),
      HumanPlayer()
    )
    val tui = Tui(controller)
    tui.run()
  }
}
