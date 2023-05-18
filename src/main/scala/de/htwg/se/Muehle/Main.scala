package de.htwg.se.Muehle

import de.htwg.se.Muehle.aview.Tui
import scala.io.StdIn.readLine
import controller.Controller
import model.{Field, PlayerList, Stone}
import model.GameStap
import model.HumanPlayer

object Main {
  def main(args: Array[String]): Unit = {
    println("Play Muehle")
    val field = Field()
    val players = PlayerList(7)
    val humanPlayer = HumanPlayer()
    val controller = Controller(
      GameStap(field, players.getFirstPlayer, players),
      humanPlayer
    )
    val tui = Tui(controller)
    tui.run
  }
}
