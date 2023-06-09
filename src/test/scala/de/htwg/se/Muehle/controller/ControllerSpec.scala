package de.htwg.se.Muehle
package controller

import java.io.StringReader

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model.{Field, Stone, PlayerList, Player, GameStap, AIPlayer, HumanPlayer}
import util.{Observable, Event, Observer}
import java.awt.Color

class ControllerSpec extends AnyWordSpec with Matchers {
  val field = Field()
  val players = PlayerList(7)
  val controller =
    Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
  "A Controller" when {
    "creating input text" should {
      "return the correct output" in {
        val expectedOutput =
          s"Give the number or the field where you want to set a stone, ${controller.gamefield.player.name} or q (quit)" + "\n" + controller.gamefield.playerlist
            .printStonesToSet()
      }
    }
    "converting to a string" should {
      "return the correct representation of the field" in {
        val field: Field = Field()
        val players: PlayerList =
          PlayerList(7)
        val controller: Controller = Controller(
          GameStap(field, players.getFirstPlayer, players),
          AIPlayer()
        )
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
      val field: Field = Field()
      val players: PlayerList = PlayerList(4)
      val controller: Controller = Controller(
        GameStap(field, players.getFirstPlayer, players),
        HumanPlayer()
      )
      controller.undo
      controller.gamefield.field should be(Field())
      controller.redo
      controller.gamefield.field should be(
        Field()
      )
      controller.put(1, -1)
      controller.put(2, -1)
      controller.put(10, -1)
      controller.put(3, -1)
      val expectedRound4 = field
        .setStone(1, Stone.White)
        .setStone(10, Stone.White)
        .setStone(3, Stone.Black)
        .setStone(2, Stone.Black)
      controller.gamefield.field should be(expectedRound4)

      controller.put(22, -1)
      controller.mill(2)
      val expectedRound5 = field
        .setStone(1, Stone.White)
        .setStone(10, Stone.White)
        .setStone(22, Stone.White)
        .setStone(3, Stone.Black)
      controller.gamefield.field should be(expectedRound5)
      controller.put(6, -1)
      controller.put(7, -1)
      controller.put(8, -1)
      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(22, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(3, Stone.Black)
      )
      controller.put(23, 22)
      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(23, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(3, Stone.Black)
      )
      controller.undo
      controller.put(23, 22)
      controller.put(24, 3)
      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(23, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(24, Stone.Black)
      )
      controller.put(22, 23)
      controller.mill(23)
      controller.mill(24)

      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(22, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
      )

      controller.undo
      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(23, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(24, Stone.Black)
      )
      /*
      controller.redo
      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(22, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
      )
       */
    }
    "simulate set complett game with 4 stones as Singelplayer" in {
      val field: Field = Field()
      val players: PlayerList = PlayerList(4)
      val controller: Controller = Controller(
        GameStap(field, players.getFirstPlayer, players),
        AIPlayer()
      )
      controller.put(1, -1)
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 3, 1), Player(Stone.Black, 3, 1)))
      )
      val simulatefield = Field()
        .setStone(1, Stone.White)
        .setStone(15, Stone.White)
        .setStone(20, Stone.White)
        .setStone(19, Stone.Black)
        .setStone(22, Stone.Black)
        .setStone(20, Stone.Black)
      val playerStoneski: List[Int] = List(19, 20, 22)
      val r = AIPlayer(playerStoneski)
      controller.playerstrategy = r

      controller.gamefield = GameStap(
        simulatefield,
        Player(Stone.White, 0, 3),
        PlayerList(List(Player(Stone.White, 0, 3), Player(Stone.Black, 0, 3)))
      )
      controller.put(10, 1)
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 0, 3), Player(Stone.Black, 0, 3)))
      )
      val simulatefieldtomove = Field()
        .setStone(1, Stone.White)
        .setStone(15, Stone.White)
        .setStone(20, Stone.White)
        .setStone(5, Stone.White)
        .setStone(19, Stone.Black)
        .setStone(22, Stone.Black)
        .setStone(20, Stone.Black)
        .setStone(3, Stone.Black)
      val playerStoneskitomove: List[Int] = List(19, 20, 22, 5)
      val playerstrategymove = AIPlayer(playerStoneskitomove)
      controller.playerstrategy = playerstrategymove
      controller.gamefield = GameStap(
        simulatefieldtomove,
        Player(Stone.White, 0, 4),
        PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
      )
      controller.put(8, 5)
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
      )
    }
    "case the put is not possible" in {
      val field = Field().setStone(1, Stone.White)
      val players = PlayerList(7)
      val controller =
        Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
      controller.put(1, -1)
      controller.gamefield.field should be(field.setStone(1, Stone.White))
    }
  }
  "calling PlayerStatics" should {
    "return the correct player statistics" in {
      val field = Field()
      val players = PlayerList(2)
      val controller =
        Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
      val (stonesToPut1, stonesInField1, stonesToPut2, stonesInField2) =
        controller.PlayerStatics()
      stonesToPut1 should be(2)
      stonesInField1 should be(0)
      stonesToPut2 should be(2)
      stonesInField2 should be(0)
    }
  }

  "calling getGameState" should {
    "return the correct game state" in {
      val field =
        Field()
          .setStone(1, Stone.White)
          .setStone(
            2,
            Stone.Black
          )
      val players = PlayerList(2)
      val controller =
        Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
      controller.getGameState(3) should be(1) // Stone.Empty
      controller.getGameState(1) should be(2) // Stone.White
      controller.getGameState(2) should be(3) // Stone.Black
    }
  }

  "calling handleMillCase" should {
    "return the correct result" in {
      val con = Controller(
        GameStap(
          Field()
            .setStone(1, Stone.White)
            .setStone(2, Stone.White)
            .setStone(3, Stone.White)
            .setStone(4, Stone.Black),
          PlayerList(7).getFirstPlayer,
          PlayerList(7)
        ),
        AIPlayer()
      )
      con.handleMillCase(3) should be(false)
      val controller =
        Controller(
          GameStap(Field(), PlayerList(2).getFirstPlayer, PlayerList(2)),
          AIPlayer()
        )
      controller.handleMillCase(1) should be(true)
    }
  }

  "calling iswhite" should {
    "return the correct color" in {
      val field =
        Field()
      val players = PlayerList(2)
      val controller =
        Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
      controller.iswhite(Color.WHITE) should be(Color.BLACK)
      controller.iswhite(Color.BLACK) should be(Color.WHITE)
    }
  }
}
