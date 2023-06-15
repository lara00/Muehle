package de.htwg.se.Muehle

import aview.Tui
import Default.{given}
import aview.gui.SwingGui
import com.google.inject.Injector
import com.google.inject.Guice
import de.htwg.se.Muehle.controller.controllerComponent.IController
/*
object Main {
  def main(args: Array[String]): Unit = {
    println("Play Muehle")
    val injector: Injector = Guice.createInjector(new Module())
    val controller = injector.getInstance(classOf[IController])
    val tui = new Tui()
    val gui = new SwingGui()
    tui.run()
  }
}
*/
object Main_ {
  def main(args: Array[String]): Unit =
    println("Play Muehle")
    val tui = Tui()
    val gui = new SwingGui()
    tui.run()
}
  
