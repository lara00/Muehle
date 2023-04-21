package de.htwg.se.Muehle.aview

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import de.htwg.se.Muehle.model.{Field, PlayerList, Player, Stone}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Scanner

class TuiSpec extends AnyFlatSpec with Matchers {
  val tui = new Tui()
  val player1 = Player(Stone.White, 3, 0)
  val player2 = Player(Stone.Black, 3, 0)
  val field = Field()
  val playerList = PlayerList(List(player1, player2))

  "playStone" should "place a stone in the field" in {
    val result = field.setStone(1, Stone.White)
    tui.playStone(1, player1, field, playerList) should be(result)
  }

  it should "not place a stone in the field if the position is already occupied" in {
    val field = Field().setStone(1, player1.name)
    tui.playStone(1, player1, field, playerList) should be(field)
  }
  "The game" should "allow two players to play a complete game of Someone" in {
    val inputStream = new ByteArrayInputStream(s"""3
         |WrongValue
         |8
         |8
         |1
         |10
         |11
         |12
         |13
         |14
         |15
      """.stripMargin.getBytes())
    val outputStream = new ByteArrayOutputStream()
    Console.withIn(inputStream) {
      Console.withOut(outputStream) {
        tui.processInput("n3")
      }
    }
    assert(outputStream.toString.contains("EMPTY"))
  }
  it should "allow a player to capture an opponent's stone" in {
    val inputStream = new ByteArrayInputStream(
      s"""3
       |8
       |8
       |1
       |10
       |11
       |12
       |13
       |14
       |15
       |16
       |17
       |3
       |2
       |4
       |5
       |6
       |q
    """.stripMargin.getBytes()
    )
    val outputStream = new ByteArrayOutputStream()
    Console.withIn(inputStream) {
      Console.withOut(outputStream) {
        tui.processInput("n7")
      }
    }
    assert(outputStream.toString.contains("EMPTY"))
  }
  it should "allow a player to quit the game" in {
    val inputStream = new ByteArrayInputStream(
      s"""3
       |q
    """.stripMargin.getBytes()
    )
    val outputStream = new ByteArrayOutputStream()
    Console.withIn(inputStream) {
      Console.withOut(outputStream) {
        tui.processInput("n3")
      }
    }
    assert(outputStream.toString.contains("EMPTY"))
  }
}
