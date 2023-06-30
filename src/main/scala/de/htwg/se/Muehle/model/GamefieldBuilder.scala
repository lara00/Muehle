package de.htwg.se.Muehle
package model

import com.google.inject.{Injector, Guice, Key}
import com.google.inject.name.Names

import fieldComponent.IField
import gameComponent.IGameStap
import playerstrategyComponent.{IPlayerStrategy, IGameInjector}

import Default.{given}

class Gamefield(val gamesetting: IGameStap = given_IGameStap, val gamestrategy: IPlayerStrategy)

class GamefieldBuilder():
  private var stonetoput: IGameStap = given_IGameStap
  private var singlegamer: IPlayerStrategy = given_IPlayerStrategy
  
  def addStonesToPut(quantity: Int): GamefieldBuilder =
    stonetoput = stonetoput.newGamestap(given_IField, PlayerList(quantity).getFirstPlayer, PlayerList(quantity))
    this

  def addSingleGamer(singelegamer: Boolean): GamefieldBuilder =
    singelegamer match
      case true  => singlegamer = IGameInjector.createInjector().getInstance(Key.get(classOf[IPlayerStrategy], Names.named("AIPlayer"))); this
      case false => singlegamer = IGameInjector.createInjector().getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer"))); this

  def build(): Gamefield = new Gamefield(stonetoput, singlegamer)
