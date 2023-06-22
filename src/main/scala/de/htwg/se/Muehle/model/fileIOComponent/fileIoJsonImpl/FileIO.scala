package de.htwg.se.Muehle.model.fileIOComponent.fileIoJsonImpl

import java.io.{File, FileWriter, PrintWriter}

import scala.io.Source
import scala.util.Using

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, Guice, Inject, Injector, Provides}
import com.google.inject.Key

import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.fileIOComponent.FileIOInterface
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.playerstrategyComponent.{IGameInjector, IPlayerStrategy}

class FileIO extends FileIOInterface:
  override def load: (IGameStap, IPlayerStrategy) = (GameStapIO.LoadGameStap(), PlayerConfigurator.loadPlayerStrategyName())
  override def save(gamestap: IGameStap, playerstrategy: IPlayerStrategy): Unit = 
    createDirectory("JsonImpl")
    PlayerConfigurator.savePlayerStrategy(playerstrategy)
    GameStapIO.SaveGamestap(gamestap)

  private def createDirectory(directoryPath: String): Unit =
    val directory = new File(directoryPath)
    if (!directory.exists())
      directory.mkdir()

  object PlayerConfigurator:
    private def saveToJson(filePath: String, strategy: IPlayerStrategy): Unit =
      val json = ("configuration" -> ("player" -> ("name" ->  strategy.getClass.getSimpleName)))
      val jsonString = compact(render(json))
      val writer = new PrintWriter(filePath)

    def savePlayerStrategy(player: IPlayerStrategy): Unit = saveToJson("JsonImpl/playerstrategy.json", player)

    def loadPlayerStrategyName(): IPlayerStrategy =
      val injector = IGameInjector.createInjector()
      val json = parse(scala.io.Source.fromFile("JsonImpl/playerstrategy.json").mkString)
      val playerName = (json \ "configuration" \ "player" \ "name") match
        case JString(name) => name
        case _ => ""
      playerName match
        case "AIPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("AIPlayer")))
        case "HumanPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
        case _ => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))

  object GameStapIO:
    implicit val formats: DefaultFormats.type = DefaultFormats

    private def toJson(player: IPlayer): JValue = 
      val nameField = ("name", JString(player.pname.toString))
      val stonesToPutField = ("stonesToPut", JInt(player.pstonetoput))
      val stonesInFieldField = ("stonesInField", JInt(player.pstoneinField))
      JObject(nameField, stonesToPutField, stonesInFieldField)

    private def toJsonObject(position: Int, stone: Stone): JObject = JObject("position" -> position, "stone" -> stone.toString)

    private def saveToFile(fileName: String, content: String): Unit =
      val file = new File(fileName)
      Using.resource(new FileWriter(file)) { writer =>
        writer.write(content)}

    private def loadPlayerFromJson(json: JValue): IPlayer =
      val nameStringOpt = (json \ "name").toOption.flatMap {
        case JString(name) => Some(name)
        case _ => None
      }
      val name = nameStringOpt.flatMap(nameStr => Stone.values.find(_.toString == nameStr)).getOrElse(Stone.Empty)
      val stonesToPutOpt = (json \ "stonesToPut").toOption.flatMap {
        case JInt(stones) => Some(stones.toInt)
        case _ => None
      }
      val stonesToPut = stonesToPutOpt.getOrElse(0)
      val stonesInFieldOpt = (json \ "stonesInField").toOption.flatMap {
        case JInt(stones) => Some(stones.toInt)
        case _ => None
      }
      val stonesInField = stonesInFieldOpt.getOrElse(0)
      given_IPlayer.pplayer(name, stonesToPut, stonesInField)

    private def loadFieldLineFromJson(json: JValue): Option[(Int, Stone)] =
      val positionOpt = (json \ "position").toOption.flatMap {
        case JInt(position) => Some(position.toInt)
        case _ => None
      }
      val stoneStringOpt = (json \ "stone").toOption.flatMap {
        case JString(stoneStr) => Some(stoneStr)
        case _ => None
      }
      for {
        pos <- positionOpt
        stoneStr <- stoneStringOpt
        stone <- Stone.values.find(_.toString == stoneStr)
      } yield (pos, stone)

    private def loadFieldFromFile(fileName: String): IField =
      val jsonString = Using.resource(scala.io.Source.fromFile(fileName)) { source =>
        source.mkString
      }
      val json = parse(jsonString)
      val fieldList = (json \ "field").children.flatMap(loadFieldLineFromJson)
      val fieldMap = fieldList.toMap
      given_IField.createField(fieldMap)

    private def savePlayerToFile(player: IPlayer, fileName: String): Unit =
      val playerJson = toJson(player)
      val jsonString = compact(render(playerJson))
      saveToFile(fileName, jsonString)

    private def savePlayersToFile(playerList: PlayerList): Unit = 
      savePlayerToFile(playerList.players(0), "JsonImpl/player01.json")
      savePlayerToFile(playerList.players(1), "JsonImpl/player02.json")

    private def saveFieldToFile(field: IField, fileName: String): Unit = 
      val fieldLinesJson = field.fieldmap.map { case (position, stone) => toJsonObject(position, stone) }.toList
      val jsonContent = ("field", fieldLinesJson)
      val jsonString = compact(render(jsonContent))
      saveToFile(fileName, jsonString)

    def SaveGamestap(gameState: IGameStap): Unit =
      savePlayerToFile(gameState.gplayer, "JsonImpl/player1.json")
      savePlayersToFile(gameState.gplayerlist)
      saveFieldToFile(gameState.gfield, "JsonImpl/field.json")

    def LoadGameStap(): IGameStap =
      val loadedField = loadFieldFromFile("JsonImpl/field.json")
      val loadedPlayerList = loadPlayersFromFile
      val loadedPlayer = loadPlayerFromFile("JsonImpl/player1.json")
      given_IGameStap.newGamestap(loadedField, loadedPlayer, loadedPlayerList)

    private def loadPlayersFromFile: PlayerList = 
      val loadedPlayer = loadPlayerFromFile("JsonImpl/player01.json")
      val loadedPlayer2 = loadPlayerFromFile("JsonImpl/player02.json")
      PlayerList(loadedPlayer, loadedPlayer2)

    private def loadPlayerFromFile(fileName: String): IPlayer = 
      val jsonString = scala.io.Source.fromFile(fileName).mkString
      val json = parse(jsonString)
      loadPlayerFromJson(json)