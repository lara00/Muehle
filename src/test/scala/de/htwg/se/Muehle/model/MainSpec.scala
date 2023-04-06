package de.htwg.se.Muehle.model.Main

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import java.io.ByteArrayOutputStream

class MainSpec extends AnyFlatSpec with Matchers {
  "The hello function" should "print 'Play Muehle' to the console" in {
    val output = new ByteArrayOutputStream()
    output.toString.trim should be("Play Muehle")
  }
}
