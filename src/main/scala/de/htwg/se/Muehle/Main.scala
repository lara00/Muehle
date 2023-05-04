package de.htwg.se.Muehle

import de.htwg.se.Muehle.aview.Tui
import scala.io.StdIn.readLine
import controller.Controller
import model.{Field, PlayerList}

object Main {
  def main(args: Array[String]): Unit = {
    println("Play Muehle")
    val field = Field()
    val players = PlayerList(7)
    val controller = Controller(field, players.getFirstPlayer, players)
    val tui = Tui(controller)
    tui.run
  }
}
