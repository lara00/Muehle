package de.htwg.se.Muehle

import aview.Tui
import aview.gui.SwingGui
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayer
import de.htwg.se.Muehle.model.fieldComponent.Field


object Main:
  def main(args: Array[String]): Unit =
    println("Play Muehle")
    val controller = Controller(
      GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4)),
      HumanPlayer()
    )
    val tui = Tui(controller)
    val gui = new SwingGui(controller)
    tui.run()
  
