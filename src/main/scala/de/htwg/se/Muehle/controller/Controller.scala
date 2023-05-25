package de.htwg.se.Muehle
package controller

import model.{
  Stone,
  Field,
  PlayerList,
  GameStap,
  MillHandler,
  PlayerStrategy,
  GamefieldBuilder,
  Mill
}
import util.{Observable, Event, UndoManager}

case class Controller(
    var gamefield: GameStap,
    var playerstrategy: PlayerStrategy
) extends Observable {
  val undoManager = new UndoManager[GameStap]
  def undo: Unit = {
    gamefield = undoManager.undoStep(gamefield)
    notifyObservers(Event.Set)
  }
  def redo: Unit = {
    gamefield = undoManager.redoStep(gamefield)
    notifyObservers(Event.Set)
  }
  def noStep: Unit = {
    gamefield = undoManager.noStep(gamefield)
    println("No Step")
  }
  def bildGameSet(number: Int, singlegamer: Boolean): Unit = {
    val game = new GamefieldBuilder()
      .addStonesToPut(number)
      .addSingleGamer(singlegamer)
      .build()
    gamefield = game.gamesetting
    playerstrategy = game.gamestrategy
    notifyObservers(Event.Set)
    notifyObservers(Event.Status)
  }
  def quit: Unit = notifyObservers(Event.Quit)
  def retrunfield(to: Int, from: Int): Field = {
    val game = put(to, from)
    gamefield = game
    notifyObservers(Event.Set)
    if (gamefield.player.stonetoput != 0) {
      println(inputText())
      notifyObservers(Event.Status)
    } else {
      println(inputText2())
    }
    gamefield.field
  }
  def put(to: Int, from: Int): GameStap =
    undoManager.doStep(
      gamefield,
      PutCommand(Move(gamefield, playerstrategy, to, from))
    )
  override def toString(): String = gamefield.field.toString
  def inputText(): String =
    s"Give the number or the field where you want to set a stone, ${gamefield.player.name} or q (quit)" + "\n" + gamefield.playerlist
      .printStonesToSet()
  def inputText2(): String =
    s"Give the number or the field you will move the Stone, ${gamefield.player.name} or q (quit)" + "\n"
  def isValid(a: String): Boolean = gamefield.field.isFieldValid(a)
  def printStonesToSet(): String = gamefield.playerlist.printStonesToSet()
}
