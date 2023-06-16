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
import com.google.inject.Injector
import com.google.inject.Guice
import de.htwg.se.Muehle.controller.controllerComponent.IController

class ControllerSpec extends AnyWordSpec with Matchers {
  val injector: Injector = Guice.createInjector(new Module())
  val field = injector.getInstance(classOf[IField])
  val players = PlayerList(7)
  val controller = injector.getInstance(classOf[IController])
  "A Controller" when {
    "creating input text" should {
      "return the correct output" in {
        val expectedOutput =
          s"Give the number or the field where you want to set a stone, ${controller.getGamestape.playername} or q (quit)" + "\n" + controller.getGamestape.gplayerlist
            .printStonesToSet()
      }
    }
    "converting to a string" should {
      "return the correct representation of the field" in {
        val field: IField = injector.getInstance(classOf[IField])
        val players: PlayerList =PlayerList(7)
        val controller = injector.getInstance(classOf[IController])
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
    "simulate set complete game with 4 stones" in {
      val controller = injector.getInstance(classOf[IController])
      controller.undo
      controller.redo
      controller.put(1, -1)
      controller.put(2, -1)
      controller.put(10, -1)
      controller.put(3, -1)
      val expectedRound4 = field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(3, Stone.Black).setStone(2, Stone.Black)
      controller.getField should be(expectedRound4)

      controller.put(22, -1)
      controller.mill(2)
      val expectedRound5 = field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(22, Stone.White).setStone(3, Stone.Black)
      controller.getField should be(expectedRound5)
      controller.put(6, -1)
      controller.put(7, -1)
      controller.put(8, -1)
      controller.getField should be(
        field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(22, Stone.White).setStone(7, Stone.White).setStone(6, Stone.Black).setStone(8, Stone.Black).setStone(3, Stone.Black))
      controller.put(23, 22)
      controller.getField should be(
        field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(23, Stone.White).setStone(7, Stone.White).setStone(6, Stone.Black).setStone(8, Stone.Black).setStone(3, Stone.Black))
      controller.undo
      controller.put(23, 22)
      controller.getGameStandLabelText should be ("BLACK, jump with a stone.")
      controller.put(24, 3)
      controller.getField should be(field.setStone(1, Stone.White).setStone(10, Stone.White).setStone(23, Stone.White).setStone(7, Stone.White)
          .setStone(6, Stone.Black).setStone(8, Stone.Black).setStone(24, Stone.Black))
      controller.put(22, 23)
      controller.mill(23)
      controller.mill(24)

      controller.getField should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(22, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
      )

      controller.undo
      controller.getField should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(23, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(24, Stone.Black)
      )
      controller.getGameStandLabelText should be ("WHITE, Click to place the stone, then click to move it.")
    }
    "simulate set complett game with 4 stones as Singelplayer" in {
      val controller = injector.getInstance(classOf[IController])
      controller.bildGameSet(4 , true)
      controller.put(1, -1)
      controller.getPalyerList should be(PlayerList(List(given_IPlayer.pplayer(Stone.White, 3, 1), given_IPlayer.pplayer(Stone.Black, 3, 1))))
      val simulatefield = injector.getInstance(classOf[IField]).setStone(1, Stone.White).setStone(15, Stone.White).setStone(20, Stone.White).setStone(19, Stone.Black)
        .setStone(4, Stone.Black).setStone(15, Stone.Black).setStone(1, Stone.White).setStone(10, Stone.White)
      val playerStoneski: List[Int] = List(19, 20, 22)
      val r = AIPlayer(playerStoneski)
      controller.controllerPlayerstrategy(r)
      controller.controllerGamestap(GameStap(simulatefield,given_IPlayer.pplayer(Stone.White, 1, 3),
      PlayerList(List(given_IPlayer.pplayer(Stone.White, 1, 3), given_IPlayer.pplayer(Stone.Black, 0, 4)))))
      controller.put(22, -1)
      controller.mill(4)
      controller.getPalyerList should be(
        PlayerList(List(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 3))))
      val simulatefieldtomove = injector.getInstance(classOf[IField]).setStone(1, Stone.White).setStone(15, Stone.White)
        .setStone(20, Stone.White)
        .setStone(5, Stone.White)
        .setStone(19, Stone.Black)
        .setStone(22, Stone.Black)
        .setStone(20, Stone.Black)
        .setStone(3, Stone.Black)
      val playerStoneskitomove: List[Int] = List(19, 20, 22, 5)
      val playerstrategymove = AIPlayer(playerStoneskitomove)
      controller.controllerPlayerstrategy(playerstrategymove)
      controller.controllerGamestap(GameStap(
        simulatefieldtomove,given_IPlayer.pplayer(Stone.White, 0, 4),
        PlayerList(List(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4)))))
      controller.put(8, 5)
      controller.getPalyerList should be(PlayerList(List(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4))))
    }
    "case the put is not possible" in {
      val controller = injector.getInstance(classOf[IController])
      controller.bildGameSet(7,false)
      controller.getField.setStone(1, Stone.White)

      controller.put(1, -1)
      controller.getField should be(field.setStone(1, Stone.White))
      controller.setormove should be (true)
      controller.getGameStandLabelText should be ("BLACK, press on a field to place a stone.")
      controller.playername should be (Stone.Black)
      controller.isValid("") should be (false)
      controller.printStonesToSet should be ("WHITE Player: Stone to set: W W W W W W\nBLACK Player: Stone to set: B B B B B B B")
    }
    "calling handleMillCase" should {
    "return the correct result" in {
      val con = injector.getInstance(classOf[IController])
      con.bildGameSet(7,false)
      con.put(1,-1)
      con.put(4,-1)
      con.put(10,-1)
      con.put(5,-1)
      con.put(22,-1)
      con.handleMillCase(3) should be(false)
      val controller = injector.getInstance(classOf[IController])
      controller.bildGameSet(2,true)
      controller.handleMillCase(1) should be(true)
    }
  }
  }
  "calling PlayerStatics" should {
    "return the correct player statistics" in {
      val field = injector.getInstance(classOf[IField])
      val players = PlayerList(2)
      val controller = injector.getInstance(classOf[IController])
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
      val controller = injector.getInstance(classOf[IController])
      controller.put(1,-1)
      controller.put(2,-1)
      controller.getGameState(3) should be(1) // Stone.Empty
      controller.getGameState(1) should be(2) // Stone.White
      controller.getGameState(2) should be(3) // Stone.Black
    }
  }

  "calling iswhite" should {
    "return the correct color" in {
      val controller = injector.getInstance(classOf[IController])
      controller.bildGameSet(2,true)
      controller.iswhite(Color.WHITE) should be(Color.BLACK)
      controller.iswhite(Color.BLACK) should be(Color.WHITE)
    }
  }
}