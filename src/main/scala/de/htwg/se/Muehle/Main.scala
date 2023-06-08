package de.htwg.se.Muehle

import aview.Tui
import aview.gui.SwingGui
import controller.Controller
import model.{Field, PlayerList, Stone, GameStap, HumanPlayer}

object Main:
  def main(args: Array[String]): Unit =
    println("Play Muehle")
    val controller = Controller(
      GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4)),
      HumanPlayer()
    )
    val tui = Tui(controller)
    val gui = new SwingGui(controller,true)
    tui.run()
  
