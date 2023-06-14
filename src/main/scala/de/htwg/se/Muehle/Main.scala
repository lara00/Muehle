package de.htwg.se.Muehle

import aview.Tui
import Default.{given}
import aview.gui.SwingGui

object Main {
  def main(args: Array[String]): Unit =
    println("Play Muehle")
    val tui = Tui()
    val gui = new SwingGui()
    tui.run()
}
  
