package de.htwg.se.Muehle.model

import java.io.{ByteArrayOutputStream, PrintStream}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.Main

class MainSpec extends AnyFlatSpec with Matchers {
  "Main" should "end the game when the user inputs 'end'" in {
    val inputStream = new ByteArrayInputStream("end".getBytes())
    val outputStream = new ByteArrayOutputStream()
    Console.withIn(inputStream) {
      Console.withOut(outputStream) {
        Main.main(Array.empty[String])
      }
    }
    val output = outputStream.toString.trim
    output contains "End Game"
  }
}
