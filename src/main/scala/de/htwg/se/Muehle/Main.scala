package de.htwg.se.Muehle

import aview.Tui
import aview.gui.SwingGui
import com.google.inject.Injector
import com.google.inject.Guice
import de.htwg.se.Muehle.controller.controllerComponent.IController

object Main {
  def main(args: Array[String]): Unit = {
    println("Play Muehle")
    val injector: Injector = Guice.createInjector(new Module())
    val controller = injector.getInstance(classOf[IController])
    val tui = new Tui(controller)
    val gui = new SwingGui(controller)
    tui.run()
  }
}