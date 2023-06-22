package de.htwg.se.Muehle.model.fileIOComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.AIPlayer
import de.htwg.se.Muehle.model.fileIOComponent.fileIoJsonImpl.FileIO

class fileIOSpec extends AnyWordSpec with Matchers {
  "FileIO load with:" when {
    "load the data" in {
      val fileIO_XML = de.htwg.se.Muehle.model.fieldComponent.fileIoXmlImpl.FileIO()
      val fileIO_Json = de.htwg.se.Muehle.model.fileIOComponent.fileIoJsonImpl.FileIO()
      val fileIO_CSV = de.htwg.se.Muehle.model.fileIOComponent.fileIOCSVImpl.FileIO()
      val fileIO_YAML = de.htwg.se.Muehle.model.fileIOComponent.fileIOYAMLImpl.FileIO()
      val fileIO_Avro = de.htwg.se.Muehle.model.fileIOComponent.fileIOAvroImpl.FileIO()

      fileIO_CSV.save(given_IGameStap, given_IPlayerStrategy)
      fileIO_XML.save(given_IGameStap, given_IPlayerStrategy)
      fileIO_Json.save(given_IGameStap, given_IPlayerStrategy)
      fileIO_YAML.save(given_IGameStap, given_IPlayerStrategy)
      fileIO_Avro.save(given_IGameStap, given_IPlayerStrategy)

      fileIO_CSV.load(0) should be (given_IGameStap)
      fileIO_XML.load(0) should be (given_IGameStap)
      fileIO_Json.load(0) should be (given_IGameStap)
      fileIO_YAML.load(0) should be (given_IGameStap)
      fileIO_Avro.load(0) should be (given_IGameStap)

      fileIO_CSV.save(given_IGameStap, AIPlayer())
      fileIO_XML.save(given_IGameStap, AIPlayer())
      fileIO_Json.save(given_IGameStap, AIPlayer())
      fileIO_YAML.save(given_IGameStap, AIPlayer())
      fileIO_Avro.save(given_IGameStap, AIPlayer())

      fileIO_CSV.load(0) should be (given_IGameStap)
      fileIO_XML.load(0) should be (given_IGameStap)
      fileIO_Json.load(0) should be (given_IGameStap)
      fileIO_YAML.load(0) should be (given_IGameStap)
      fileIO_Avro.load(0) should be (given_IGameStap)
    }
  }
}