package de.htwg.se.Muehle
package controller.controllerComponent.controllerBaseImpl

import model.{Stone,MillEvents,GamefieldBuilder,MoveEvents}
import util.{Observable, Event, UndoManager}
import java.awt.Color
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.controller.controllerComponent.IController
import de.htwg.se.Muehle.model.playerstrategyComponent.{IPlayerStrategy, IGameInjector}

import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import de.htwg.se.Muehle.Default.{given}

import com.google.inject.{Key, Inject}
import com.google.inject.name.Names


class Controller(using var gamefield: IGameStap, var playerstrategy: IPlayerStrategy) extends IController with Observable {
  var gamesize = gamefield.gplayer.pstonetoput;
  val undoManager = new UndoManager[IGameStap]

  def undo: Unit =
    gamefield = undoManager.undoStep(gamefield)

  def redo: Unit =
    gamefield = undoManager.redoStep(gamefield)

  def bildGameSet(number: Int, singlegamer: Boolean): Unit =
    gamesize = number
    val game = new GamefieldBuilder()
      .addStonesToPut(number)
      .addSingleGamer(singlegamer)
      .build()
    gamefield = game.gamesetting
    playerstrategy = game.gamestrategy
    notifyObservers(Event.Status)

  def quit(readsonforquit: String): Unit = notifyObservers(Event.Quit)

  def mill(delete: Int) =
    notifyObservers(Event.Status)
    val mill = gamefield.handleMill(delete)
    PutCommand(Move(mill(1), mill(0), playerstrategy, delete, 0))
    mill(1) match
      case MillEvents.DeleteStone =>
        gamefield = mill(0)
        notifyObservers(Event.Status)
        if (playerstrategy == IGameInjector.createInjector().getInstance(Key.get(classOf[IPlayerStrategy], Names.named("AIPlayer"))).getClass())
          gamefield = playerstrategy.makeMove(gamefield, 1, -1)(0)
          notifyObservers(Event.Status)
      case MillEvents.EndGame =>
        gamefield = mill(0)
        notifyObservers(Event.Quit)
      case MillEvents.WrongDelete =>
        notifyObservers(Event.Mill)

  def put(to: Int, from: Int): Unit =
    val move = playerstrategy.makeMove(gamefield, to, from)
    undoManager.doStep(gamefield,PutCommand(Move(move(1), gamefield, playerstrategy, to, from)))
    move(1) match 
      case MoveEvents.SetStone_Mill | MoveEvents.MoveStone_Mill =>
        gamefield = move(0)
        notifyObservers(Event.Status)
        notifyObservers(Event.Mill)
      case _ =>
        gamefield = move(0)
        notifyObservers(Event.Status)
        
  override def toString(): String = gamefield.gfield.toString

  def isValid(a: String): Boolean = gamefield.gfield.isFieldValid(a)

  def printStonesToSet: String = gamefield.gplayerlist.printStonesToSet()

  def PlayerStatics: (Int, Int, Int, Int) =
    val player1 = gamefield.gplayerlist.getFirstPlayer
    val player2 = gamefield.gplayerlist.getNextPlayer(player1)
    (player1.pstonetoput,player1.pstoneinField,player2.pstonetoput,player2.pstoneinField)

  def setormove: Boolean =
    gamefield.gplayer.pstonetoput != 0

  def getGameStandLabelText: String =
    gamefield.gplayer.pstonetoput match
      case 0 =>
        if (gamefield.gplayerlist.threeStonesontheField(gamefield.getNextPlayer))
          s"${gamefield.playername}, jump with a stone."
        else
          s"${gamefield.playername}, Click to place the stone, then click to move it."
      case _ => s"${gamefield.playername}, press on a field to place a stone."

  def getGameState(value: Int): Int =
    gamefield.gfield.stones_field(value) match
      case Stone.Empty => 1
      case Stone.White => 2
      case Stone.Black => 3

  def handleMillCase(index: Int): Boolean =
    gamefield.handleMill(index + 1)(1) match
      case MillEvents.DeleteStone | MillEvents.EndGame =>
        mill(index + 1)
        false
      case MillEvents.WrongDelete => true

  def iswhite(color: Color): Color =
    if (color == Color.WHITE) Color.BLACK else Color.WHITE

  def playername: Stone = gamefield.playername
}