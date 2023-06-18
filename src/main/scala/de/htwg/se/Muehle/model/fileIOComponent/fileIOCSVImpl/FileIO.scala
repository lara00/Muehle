package de.htwg.se.Muehle.model.fileIOComponent.fileIOCSVImpl

import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.fileIOComponent.FileIOInterface
import com.google.inject.name.Names
import com.google.inject.Key
import de.htwg.se.Muehle.model.playerstrategyComponent.IGameInjector
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.Stone
import com.github.tototoshi.csv._
import java.io.File
import java.io.FileWriter
import de.htwg.se.Muehle.Default.given

class FileIO extends FileIOInterface {
  override def load: (IGameStap, IPlayerStrategy) = (GamestapIO.loadGameState(), PlayerConfigurator.loadPlayerStrategyName())

  override def save(gamestap: IGameStap, playerstrategy: IPlayerStrategy): Unit = {
    createDirectory("CSVImpl")
    PlayerConfigurator.savePlayerStrategy(playerstrategy)
    GamestapIO.saveGameState(gamestap)
  }

  private def createDirectory(directoryPath: String): Unit = {
    val directory = new File(directoryPath)
    if (!directory.exists())
      directory.mkdir()
  }

  private object GamestapIO {
    private def savePlayerToFile(player: IPlayer, fileName: String): Unit = {
      val writer = CSVWriter.open(new File(fileName))
      val data = Seq(Seq(player.pname.toString, player.pstonetoput.toString, player.pstoneinField.toString))
      writer.writeAll(data)
      writer.close()
    }

    private def savePlayersToFile(playerList: PlayerList, fileName: String): Unit = {
      val writer = CSVWriter.open(new File(fileName))
      val data = playerList.players.map(player => Seq(player.pname.toString, player.pstonetoput.toString, player.pstoneinField.toString))
      writer.writeAll(data)
      writer.close()
    }

    private def loadPlayerFromFile(fileName: String): IPlayer = {
      val reader = CSVReader.open(new File(fileName))
      val data = reader.all()
      reader.close()

      val nameStr = data.head.head
      val stonesToPutStr = data.head(1)
      val stonesInFieldStr = data.head(2)

      val name = Stone.values.find(_.toString == nameStr).getOrElse(Stone.Empty)
      val stonesToPut = stonesToPutStr.toInt
      val stonesInField = stonesInFieldStr.toInt

      given_IPlayer.pplayer(name, stonesToPut, stonesInField)
    }

    private def loadPlayersFromFile(fileName: String): PlayerList = {
      val reader = CSVReader.open(new File(fileName))
      val data = reader.all()
      reader.close()

      val players = data.map { row =>
        val nameStr = row.head
        val stonesToPutStr = row(1)
        val stonesInFieldStr = row(2)
        val name = Stone.values.find(_.toString == nameStr).getOrElse(Stone.Empty)
        val stonesToPut = stonesToPutStr.toInt
        val stonesInField = stonesInFieldStr.toInt
        given_IPlayer.pplayer(name, stonesToPut, stonesInField)
      }

      PlayerList(players.toList)
    }

    private def saveFieldToFile(field: IField, fileName: String): Unit = {
      val writer = CSVWriter.open(new File(fileName))
      val lines = field.fieldmap.map { case (position, stone) =>
        Seq(position.toString, stone.toString)
      }.toList
      writer.writeAll(lines)
      writer.close()
    }

    private def loadFieldFromFile(fileName: String): IField = {
      val reader = CSVReader.open(new File(fileName))
      val lines = reader.all()
      reader.close()

      val fieldMap = lines.flatMap { line =>
        val position = line.head.toInt
        val stoneStr = line(1)
        val stone = Stone.values.find(_.toString == stoneStr).getOrElse(Stone.Empty)
        Some(position -> stone)
      }.toMap

      given_IField.createField(fieldMap)
    }

    def saveGameState(gameState: IGameStap): Unit = {
      savePlayerToFile(gameState.gplayer, "CSVImpl/player1.csv")
      savePlayersToFile(gameState.gplayerlist, "CSVImpl/players.csv")
      saveFieldToFile(gameState.gfield, "CSVImpl/field.csv")
    }

    def loadGameState(): IGameStap = {
      val loadedField = loadFieldFromFile("CSVImpl/field.csv")
      val loadedPlayerList = loadPlayersFromFile("CSVImpl/players.csv")
      val loadedPlayer = loadPlayerFromFile("CSVImpl/player1.csv")
      given_IGameStap.newGamestap(loadedField, loadedPlayer, loadedPlayerList)
    }
  }

  private object PlayerConfigurator {
    private val playerStrategyFile = "CSVImpl/playerstrategy.csv"

    private def saveToCsv(strategy: IPlayerStrategy): Unit = {
      val playerName = strategy.getClass.getSimpleName
      val data = Seq(Seq(playerName))
      val writer = CSVWriter.open(new File(playerStrategyFile))
      writer.writeAll(data)
      writer.close()
    }

    def savePlayerStrategy(player: IPlayerStrategy): Unit =
      saveToCsv(player)

    def loadPlayerStrategyName(): IPlayerStrategy = {
      val injector = IGameInjector.createInjector()
      val reader = CSVReader.open(new File(playerStrategyFile))
      val playerName = reader.all().headOption.flatMap(_.headOption).getOrElse("HumanPlayer")
      reader.close()

      playerName match {
        case "AIPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("AIPlayer")))
        case "HumanPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
        case _ => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
      }
    }
  }
}
