package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.aview.Tui
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]): Unit = {
    println("Play Muehle")
    val tui = Tui()
    tui.processInput(readLine().toString())
  }
}
