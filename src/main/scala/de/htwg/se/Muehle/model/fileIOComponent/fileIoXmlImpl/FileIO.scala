package de.htwg.se.Muehle.model.fieldComponent.fileIoXmlImpl

import java.io.{File, FileWriter, PrintWriter}

import scala.util.Using
import scala.xml.{Elem, XML, Node}

import com.google.inject.{AbstractModule, Guice, Inject, Injector, Provides}
import com.google.inject.name.{Named, Names}
import com.google.inject.Key

import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.{Stone, PlayerList}
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.fileIOComponent.FileIOInterface
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.playerstrategyComponent.{IGameInjector, IPlayerStrategy}

class FileIO extends FileIOInterface:
  override def load: (IGameStap, IPlayerStrategy) = (GamestapIO.LoadGameStap(), PlayerConfigurator.loadPlayerStrategyName())
  override def save(gamestap: IGameStap, playerstrategy: IPlayerStrategy): Unit =
    createDirectory("XMLImpl")
    PlayerConfigurator.savePlayerStrategy(playerstrategy)
    GamestapIO.SaveGamestap(gamestap)

  private def createDirectory(directoryPath: String): Unit = 
    val directory = new File(directoryPath)
    if (!directory.exists())
      directory.mkdir()

  object PlayerConfigurator {
    private def saveToXml(filePath: String, strategy: IPlayerStrategy): Unit =
      val playerName = strategy.getClass.getSimpleName
      val xml =
        <configuration>
          <player>
            <name>{playerName}</name>
          </player>
        </configuration>
      val xmlString = xml.toString()
      val writer = new PrintWriter(filePath)
      writer.write(xmlString)
      writer.close()

    def savePlayerStrategy(player: IPlayerStrategy): Unit =
      PlayerConfigurator.saveToXml("XMLImpl/playerstrategy.xml", player)

    def loadPlayerStrategyName(): IPlayerStrategy =
      val injector = IGameInjector.createInjector()
      val xml = XML.loadFile("XMLImpl/playerstrategy.xml")
      val playerNameNodes = xml \\ "name"

      playerNameNodes.headOption.map(_.text) match
        case Some("AIPlayer") => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("AIPlayer")))
        case Some("HumanPlayer") => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
        case _ => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
  }

  object GamestapIO {
    private def toXmlPlayer(player: IPlayer): Elem =
      <player>
        <name>{player.pname.toString}</name>
        <stonesToPut>{player.pstonetoput}</stonesToPut>
        <stonesInField>{player.pstoneinField}</stonesInField>
      </player>

    private def toXmlField(position: Int, stone: Stone): Elem =
      <fieldLine>
        <position>{position}</position>
        <stone>{stone.toString}</stone>
      </fieldLine>

    private def savePlayersToFile(playerList: PlayerList, fileName: String): Unit =
      val playersXml = playerList.players.map(toXmlPlayer)
      val xmlContent =
        <players>
          {playersXml}
        </players>
      saveToFile(fileName, xmlContent.toString())

    private def savePlayerToFile(player: IPlayer, fileName: String): Unit =
      val playerXml = toXmlPlayer(player)
      val xmlString = playerXml.toString()
      saveToFile(fileName, xmlString)

    private def saveToFile(fileName: String, content: String): Unit =
      val file = new File(fileName)
      Using.resource(new FileWriter(file)) { writer => writer.write(content)}

    private def saveFieldToFile(field: IField, fileName: String): Unit =
      val fieldLinesXml = field.fieldmap.map { case (position, stone) => toXmlField(position, stone) }.toList
      val xmlContent = <field>{fieldLinesXml}</field>
      saveToFile(fileName, xmlContent.toString())

    private def loadPlayerFromFile(fileName: String): IPlayer =
      val xml = XML.loadFile(fileName)
      loadPlayerFromXml(xml)

    private def loadPlayerFromXml(xml: Elem): IPlayer =
      val nameString = (xml \ "name").text
      val name =
        Stone.values.find(_.toString == nameString).getOrElse(Stone.Empty)
      val stonesToPut = (xml \ "stonesToPut").text.toInt
      val stonesInField = (xml \ "stonesInField").text.toInt
      given_IPlayer.pplayer(name, stonesToPut, stonesInField)

    private def loadPlayersFromFile(fileName: String): PlayerList =
      val xml = XML.loadFile(fileName)
      val players = (xml \ "player").map { playerXml =>
        val nameString = (playerXml \ "name").text
        val name = Stone.values.find(_.toString == nameString).getOrElse(Stone.Empty)
        val stonesToPut = (playerXml \ "stonesToPut").text.toInt
        val stonesInField = (playerXml \ "stonesInField").text.toInt
        given_IPlayer.pplayer(name, stonesToPut, stonesInField)
      }
      PlayerList(players.toList)

    private def loadFieldLineFromXml(xml: Node): Option[(Int, Stone)] =
      val positionOpt = (xml \ "position").headOption.map(_.text.toInt)
      val stoneStringOpt = (xml \ "stone").headOption.map(_.text)

      for {
        pos <- positionOpt
        stoneStr <- stoneStringOpt
        stone <- Stone.values.find(_.toString == stoneStr)
      } yield (pos, stone)

    private def loadFieldFromFile(fileName: String): IField =
      val xml = XML.loadFile(fileName)
      val fieldLines = (xml \ "fieldLine").flatMap(loadFieldLineFromXml)
      val fieldMap = fieldLines.toMap
      given_IField.createField(fieldMap)

    def SaveGamestap(g: IGameStap): Unit =
      savePlayerToFile(g.gplayer, "XMLImpl/player1.xml")
      savePlayersToFile(g.gplayerlist, "XMLImpl/players.xml")
      saveFieldToFile(g.gfield, "XMLImpl/field.xml")

    def LoadGameStap(): IGameStap =
      val loadedField = loadFieldFromFile("XMLImpl/field.xml")
      val loadedPlayerList = loadPlayersFromFile("XMLImpl/players.xml")
      val loadedPlayer = loadPlayerFromFile("XMLImpl/player1.xml")
      given_IGameStap.newGamestap(loadedField, loadedPlayer, loadedPlayerList)
  }