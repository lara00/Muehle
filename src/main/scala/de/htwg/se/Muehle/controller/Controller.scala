package de.htwg.se.Muehle
package controller

import model.Stone
import model.Field
import model.PlayerList
import model.GameStap
import model.GameHandler
import model.Gamemove
import model.MillHandler
import model.PlayerStrategy
import model.GamefieldBuilder
import model.VerticalAndHorizontalMillChecker
import model.Mill

import util.Observable
import util.Event
import de.htwg.se.Muehle.model.GamefieldBuilder

case class Controller(
    var gamefield: GameStap,
    var playerstrategy: PlayerStrategy
) extends Observable {
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

  def isMill(delete: Int): Field = {
    println("Moin")
    val newgamestap =
      MillHandler(gamefield).funktion(delete, playerstrategy)
    gamefield == newgamestap match {
      case true  => notifyObservers(Event.IsMill)
      case false => gamefield = newgamestap
    }
    println(gamefield)
    gamefield.field
  }

  def put(to: Int, from: Int): Field = {
    println(playerstrategy)
    val game = playerstrategy.makeMove(gamefield, to, from)
    (gamefield.player.name == game.player.name && gamefield.playerlist
      .getNextPlayer(gamefield.player)
      .stonetoput == game.playerlist
      .getNextPlayer(game.player)
      .stonetoput) && gamefield.field != game.field match {
      case true =>
        gamefield = game
        notifyObservers(Event.Set)
        if (
          Mill(gamefield.field, VerticalAndHorizontalMillChecker)
            .existsMill()
        ) {
          notifyObservers(Event.IsMill)
        }
        gamefield.field
      case false =>
        gamefield = game
        notifyObservers(Event.Set)
        if (gamefield.player.stonetoput != 0)
          println(inputText())
          notifyObservers(Event.Status)
        else println(inputText2())
        gamefield.field
    }
  }
  override def toString(): String = gamefield.field.toString
  def inputText(): String =
    s"Give the number or the field where you want to set a stone, ${gamefield.player.name} or q (quit)" + "\n" + gamefield.playerlist
      .printStonesToSet()

  def inputText2(): String =
    s"Give the number or the field you will move the Stone, ${gamefield.player.name} or q (quit)" + "\n"

  def isValid(a: String): Boolean = gamefield.field.isFieldValid(a)

  def printStonesToSet(): String = gamefield.playerlist.printStonesToSet()
}
