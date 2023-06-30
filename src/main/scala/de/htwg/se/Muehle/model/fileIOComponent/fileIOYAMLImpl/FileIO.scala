package de.htwg.se.Muehle.model.fileIOComponent.fileIOYAMLImpl

import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.PlayerList
import java.nio.file.Files
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import org.yaml.snakeyaml.Yaml
import de.htwg.se.Muehle.model.fieldComponent.IField
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.model.playerstrategyComponent.IGameInjector
import com.google.inject.Key
import com.google.inject.name.Names
import java.nio.file.Paths
import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.fileIOComponent.FileIOInterface
import scala.jdk.CollectionConverters._

import scala.collection.JavaConverters

class FileIO extends FileIOInterface:
  override def load: (IGameStap, IPlayerStrategy) = (GamestapIO.loadGameState(), PlayerConfigurator.loadPlayerStrategyName)
  override def save(gamestap: IGameStap, playerstrategy: IPlayerStrategy): Unit =
    createDirectory("YamlImpl")
    PlayerConfigurator.savePlayerStrategy(playerstrategy)
    GamestapIO.saveGameState(gamestap)

  private def createDirectory(directoryPath: String): Unit =
    val directory = new File(directoryPath)
    if (!directory.exists())
      directory.mkdir()

  object PlayerConfigurator:
    private def saveToYaml(filePath: String, playerName: String): Unit =
      val yaml = new Yaml()
      val yamlString = yaml.dump(playerName)
      val writer = new FileWriter(filePath)
      writer.write(yamlString)
      writer.close()

    def savePlayerStrategy(player: IPlayerStrategy): Unit = PlayerConfigurator.saveToYaml("YamlImpl/playerstrategy.yaml", player.getClass.getSimpleName)

    def loadPlayerStrategyName: IPlayerStrategy = 
      val injector = IGameInjector.createInjector()
      val yaml = new Yaml()
      val file = new File("YamlImpl/playerstrategy.yaml")
      val inputStream = new FileInputStream(file)
      val playerName = yaml.load(inputStream).asInstanceOf[String]
      playerName match 
        case "AIPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("AIPlayer")))
        case "HumanPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
        case _ => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
      
  object GamestapIO:
    def savePlayersToFile(playerList: PlayerList): Unit =
      savePlayerToFile(playerList.players(0), "YamlImpl/player01.yaml")
      savePlayerToFile(playerList.players(1), "YamlImpl/Player2.yaml")

    def savePlayerToFile(player: IPlayer, fileName: String): Unit =
      val playerYaml = JavaConverters.mapAsJavaMap(Map("name" -> player.pname.toString,"stonesToPut" -> player.pstonetoput.toString,"stonesInField" -> player.pstoneinField.toString))
      val yaml = new Yaml()
      val yamlString = yaml.dump(playerYaml)
      saveToFile(fileName, yamlString)

    def saveFieldToFile(field: IField, fileName: String): Unit = 
      val fieldMap = new java.util.HashMap[String, String]()
      field.fieldmap.foreach { case (position, stone) =>
        fieldMap.put(position.toString, stone.toString)
      }
      val yaml = new Yaml()
      val yamlString = yaml.dump(fieldMap)
      val file = new File(fileName)
      val writer = new FileWriter(file)
      writer.write(yamlString)
      writer.close()

    def loadFieldFromFile(fileName: String): IField =
      val yaml = new Yaml()
      val file = new File(fileName)
      val inputStream = new FileInputStream(file)
      val fieldMap = yaml.load(inputStream).asInstanceOf[java.util.Map[String, String]].asScala.toMap

      val loadedFields = fieldMap.flatMap { case (position, stoneString) =>
        val stone = Stone.values.find(_.toString == stoneString).getOrElse(Stone.Empty)
        Some(position.toInt -> stone)
      }
      given_IField.createField(loadedFields)

    private def saveToFile(fileName: String, content: String): Unit =
      val file = new File(fileName)
      val writer = new FileWriter(file)
      writer.write(content)
      writer.close()

    def loadPlayerFromYaml(data: String): IPlayer =
      val yamlData = loadYamlFromFile(data)
      val yaml = new Yaml()
      val playerConfig = yaml.load(yamlData).asInstanceOf[java.util.Map[String, String]]
      val nameString = playerConfig.getOrDefault("name", "WHITE")
      val name = Stone.values.find(_.toString == nameString).getOrElse(Stone.Empty)
      val stonesToPut = playerConfig.getOrDefault("stonesToPut", "4").toInt
      val stonesInField = playerConfig.getOrDefault("stonesInField", "0").toInt
      given_IPlayer.pplayer(name, stonesToPut, stonesInField)

    def loadYamlFromFile(filePath: String): String =
      val content = new String(Files.readAllBytes(Paths.get(filePath)))
      content

    def loadPlayersFromFile: PlayerList =
      val loadedPlayer1 = GamestapIO.loadPlayerFromYaml("YamlImpl/player01.yaml")
      val loadedPlayer2 = GamestapIO.loadPlayerFromYaml("YamlImpl/Player2.yaml")
      PlayerList(loadedPlayer1, loadedPlayer2)

    def saveGameState(gameState: IGameStap): Unit =
      savePlayerToFile(gameState.gplayer, "YamlImpl/player1.yaml")
      savePlayersToFile(gameState.gplayerlist)
      saveFieldToFile(gameState.gfield, "YamlImpl/field.yaml")

    def loadGameState(): IGameStap =
      val loadedField = loadFieldFromFile("YamlImpl/field.yaml")
      val loadedPlayerList = loadPlayersFromFile
      val loadedPlayer = loadPlayerFromYaml("YamlImpl/player01.yaml")
      given_IGameStap.newGamestap(loadedField, loadedPlayer, loadedPlayerList)