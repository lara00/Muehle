package de.htwg.se.Muehle
package controller

import java.io.StringReader

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model.{Stone, PlayerList}
import util.{Observable, Event, Observer}
import java.awt.Color
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.*
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl.HumanPlayer
import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.AIPlayer
import de.htwg.se.Muehle.model.MillEvents

class ControllerSpec extends AnyWordSpec with Matchers {
  val field = given_IField
  val players = PlayerList(7)
  val controller = Controller()
  "A Controller" when {
    "creating input text" should {
      "return the correct output" in {
        val expectedOutput =
          s"Give the number or the field where you want to set a stone, ${controller.gamefield.playername} or q (quit)" + "\n" + controller.gamefield.gplayerlist
            .printStonesToSet()
      }
    }
    "converting to a string" should {
      "return the correct representation of the field" in {
        val field: IField = given_IField
        val players: PlayerList =PlayerList(7)
        val controller = Controller()
        controller.toString should be(
          field.toString
        )
      }
    }
    "quit" should {
      "notify observers with the quit event" in {
        var event: Option[Event] = None
        controller.add(new Observer {
          override def update(e: Event): Unit = event = Some(e)
        })
        controller.quit("")
        event should contain(Event.Quit)
      }
    }
    "simulate set complete game with 4 stones and test undo/redo" in {
      val controller: Controller = Controller()
      // Test undo/redo, on a empty
      controller.undo
      controller.redo
      controller.put(1, -1)
      controller.undo
      controller.redo
      /*undo redo set*/
      controller.put(2, -1)
      controller.put(2, -1)
      controller.put(10, -1)
      controller.put(3, -1)
      val expectedRound4 = field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(3, Stone.Black).setStone(2, Stone.Black)
      controller.gamefield.gfield should be(expectedRound4)
      //mill in field, player white (1,10,22), delete stone 2 from player black
      controller.put(22, -1)
      controller.mill(2)
      //test undo/redo on mill
      controller.undo
      controller.redo
      val expectedRound5 = field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(22, Stone.White).setStone(3, Stone.Black)
      controller.gamefield.gfield should be(expectedRound5)
      controller.put(6, -1)
      controller.put(7, -1)
      controller.put(8, -1)
      controller.gamefield.gfield should be(field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(22, Stone.White).setStone(7, Stone.White).setStone(6, Stone.Black).setStone(8, Stone.Black).setStone(3, Stone.Black))
      /*move a stone*/
      controller.put(23, 22)
      controller.gamefield.gfield should be(field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(23, Stone.White).setStone(7, Stone.White).setStone(6, Stone.Black).setStone(8, Stone.Black).setStone(3, Stone.Black))
      /*test undo/redo on move*/
      controller.undo
      controller.redo
      controller.getGameStandLabelText should be ("BLACK, jump with a stone.")
      controller.put(24, 3)
      controller.gamefield.gfield should be(field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(23, Stone.White).setStone(7, Stone.White).setStone(6, Stone.Black).setStone(8, Stone.Black).setStone(24, Stone.Black))
      controller.put(22, 23)
      controller.mill(23)
      controller.mill(24)
      controller.gamefield.gfield should be(field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(22, Stone.White).setStone(7, Stone.White).setStone(6, Stone.Black).setStone(8, Stone.Black))
      controller.getGameStandLabelText should be ("BLACK, Click to place the stone, then click to move it.")
    }
    "case the put is not possible" in {
      val controller = Controller()
      controller.bildGameSet(7,false)
      controller.gamefield.gfield.setStone(1, Stone.White)
      controller.put(1, -1)
      controller.gamefield.gfield should be(field.setStone(1, Stone.White))
      controller.setormove should be (true)
      controller.getGameStandLabelText should be ("BLACK, press on a field to place a stone.")
      controller.playername should be (Stone.Black)
      controller.isValid("") should be (false)
      controller.printStonesToSet should be ("WHITE Player: Stone to set: W W W W W W\nBLACK Player: Stone to set: B B B B B B B")
    }
    "calling handleMillCase" should {
    "return the correct result" in {
      val con = Controller()
      con.bildGameSet(7,false)
      con.put(1,-1)
      con.put(4,-1)
      con.put(10,-1)
      con.put(5,-1)
      con.put(22,-1)
      con.handleMillCase(3) should be(false)
      val controller = Controller()
      controller.bildGameSet(2,true)
      controller.handleMillCase(1) should be(true)
    }
  }
  }
  "calling PlayerStatics" should {
    "return the correct player statistics" in {
      val field = given_IField
      val players = PlayerList(2)
      val controller = Controller()
      controller.bildGameSet(2,false)
      val (stonesToPut1, stonesInField1, stonesToPut2, stonesInField2) = controller.PlayerStatics
      stonesToPut1 should be(2)
      stonesInField1 should be(0)
      stonesToPut2 should be(2)
      stonesInField2 should be(0)
    }
  }
  "calling getGameState" should {
    "return the correct game state" in {
      val players = PlayerList(2)
      val controller = Controller()
      controller.put(1,-1)
      controller.put(2,-1)
      controller.getGameState(3) should be(1) // Stone.Empty
      controller.getGameState(1) should be(2) // Stone.White
      controller.getGameState(2) should be(3) // Stone.Black
    }
  }
  "calling iswhite" should {
    "return the correct color" in {
      val controller = Controller()
      controller.bildGameSet(2,true)
      controller.iswhite(Color.WHITE) should be(Color.BLACK)
      controller.iswhite(Color.BLACK) should be(Color.WHITE)
    }
    "controller save and load" in {
      controller.save
      controller.load 
      given_FileIOInterface.load(0) should be (controller.gamefield)
    }
  }
  "aiplayer, shound make this turn after mill" should {
    val controller = Controller()
    controller.playerstrategy = AIPlayer()
    controller.deleteStone(controller.gamefield, MillEvents.DeleteStone, 1)
  }
}