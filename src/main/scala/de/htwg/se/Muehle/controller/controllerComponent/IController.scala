package de.htwg.se.Muehle.controller.controllerComponent

import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.util.Observable
import de.htwg.se.Muehle.model.Stone
import java.awt.Color

trait IController extends Observable {
  var gamesize: Int

  def undo: Unit
  def redo: Unit
  def save: Unit
  def load: Unit

  def bildGameSet(number: Int, singlegamer: Boolean): Unit

  def quit(readsonforquit: String): Unit
  def mill(delete: Int): Unit
  def put(to: Int, from: Int): Unit

  def isValid(a: String): Boolean
  def printStonesToSet: String
  def PlayerStatics: (Int, Int, Int, Int)
  def setormove: Boolean
  def getGameStandLabelText: String
  def getGameState(value: Int): Int
  def handleMillCase(index: Int): Boolean
  def iswhite(color: Color): Color
  def playername: Stone

  override def toString(): String

}