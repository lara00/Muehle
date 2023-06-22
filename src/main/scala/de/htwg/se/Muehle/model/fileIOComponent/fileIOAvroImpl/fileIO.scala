package de.htwg.se.Muehle.model.fileIOComponent.fileIOAvroImpl

import java.io.File
import scala.collection.mutable.ListBuffer
import scala.util.{Try, Success, Failure}

import com.google.inject.name.Names
import com.google.inject.Key

import org.apache.avro.Schema
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.generic.{GenericData, GenericDatumReader, GenericDatumWriter, GenericRecord}
import org.apache.avro.specific.SpecificDatumWriter

import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.fileIOComponent.FileIOInterface
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.playerstrategyComponent.{IGameInjector, IPlayerStrategy}
import de.htwg.se.Muehle.model.{PlayerList, Stone}

class FileIO extends FileIOInterface:
  override def load: (IGameStap, IPlayerStrategy) = (GameStapIO.LoadGameStap(), PlayerConfigurator.loadPlayerStrategyName)
  override def save(gamestap: IGameStap, playerstrategy: IPlayerStrategy): Unit = 
    createDirectory("AvroImpl")
    PlayerConfigurator.savePlayerStrategy(playerstrategy)
    GameStapIO.SaveGamestap(gamestap)

  private def createDirectory(directoryPath: String): Unit =
    val directory = new File(directoryPath)
    if (!directory.exists())
      directory.mkdir()

object GameStapIO:
  private val AvroSchemaString =
    """
      |{
      |  "type": "record",
      |  "name": "Player",
      |  "fields": [
      |    { "name": "name", "type": "string" },
      |    { "name": "stonesToPut", "type": "int" },
      |    { "name": "stonesInField", "type": "int" }
      |  ]
      |}
      |""".stripMargin

  private val schemaString =
      """
        |{
        |  "type": "record",
        |  "name": "FieldData",
        |  "fields": [
        |    {
        |      "name": "position",
        |      "type": "int"
        |    },
        |    {
        |      "name": "stone",
        |      "type": "string"
        |    }
        |  ]
        |}
        |""".stripMargin


  private val AvroSchema: Schema = new Schema.Parser().parse(AvroSchemaString)

  def createGenericRecord(player: IPlayer): GenericRecord =
    val record = new GenericData.Record(AvroSchema)
    record.put("name", player.pname.toString)
    record.put("stonesToPut", player.pstonetoput)
    record.put("stonesInField", player.pstoneinField)
    record

  def saveToFile(player: IPlayer, avroDataFileName: String): Unit =
    val record = createGenericRecord(player)
    val file = new File(avroDataFileName)
    val datumWriter: SpecificDatumWriter[GenericRecord] = new SpecificDatumWriter[GenericRecord](AvroSchema)
    val dataFileWriter: DataFileWriter[GenericRecord] = new DataFileWriter[GenericRecord](datumWriter)
    dataFileWriter.create(AvroSchema, file)
    dataFileWriter.append(record)
    dataFileWriter.close()
  
  def loadFromFile(fileName: String): IPlayer =
    val file = new File(fileName)
    val datumReader: GenericDatumReader[GenericRecord] = new GenericDatumReader[GenericRecord](AvroSchema)
    val dataFileReader: DataFileReader[GenericRecord] = new DataFileReader[GenericRecord](file, datumReader)
    val genericRecord = dataFileReader.next()
    val name = genericRecord.get("name").toString
    val stone = Stone.values.find(_.toString == name).getOrElse(Stone.Empty)
    val stonesToPut = genericRecord.get("stonesToPut").asInstanceOf[Int]
    val stonesInField = genericRecord.get("stonesInField").asInstanceOf[Int]
    given_IPlayer.pplayer(stone, stonesToPut, stonesInField)
  
  def savePlayersToFile(playerList: PlayerList): Unit = 
    saveToFile(playerList.players(0), "AvroImpl/player01.avro")
    saveToFile(playerList.players(1), "AvroImpl/Player2.avro")
  
  def loadPlayers: PlayerList =
    val player1 = loadFromFile("AvroImpl/player01.avro")
    val player2 = loadFromFile("AvroImpl/Player2.avro")
    PlayerList(player1, player2)
  
  def loadFieldFromFile(fileName: String): IField =
    val schema = new Schema.Parser().parse(schemaString)
    val datumReader: GenericDatumReader[GenericRecord] = new GenericDatumReader[GenericRecord](schema)
    val dataFileReader: DataFileReader[GenericRecord] = new DataFileReader[GenericRecord](new File(fileName), datumReader)
    val loadedFields = new ListBuffer[(Int, Stone)]
    while (dataFileReader.hasNext) {
      val record = dataFileReader.next()
      val position = record.get("position").asInstanceOf[Int]
      val stoneString = record.get("stone").toString
      val stone = Stone.values.find(_.toString == stoneString).getOrElse(Stone.Empty)
      loadedFields += (position -> stone)
    }
    dataFileReader.close()
    given_IField.createField(loadedFields.toMap)
  
  def saveToAvro(fileName: String, field: IField): Unit =
    val schema = new Schema.Parser().parse(schemaString)
    val datumWriter: GenericDatumWriter[GenericRecord] = new GenericDatumWriter[GenericRecord](schema)
    val dataFileWriter: DataFileWriter[GenericRecord] = new DataFileWriter[GenericRecord](datumWriter)
    dataFileWriter.create(schema, new File(fileName))
    field.fieldmap.foreach { case (position, stone) =>
      val record: GenericRecord = new GenericData.Record(schema)
      record.put("position", position)
      record.put("stone", stone.toString)
      dataFileWriter.append(record)
    }
    dataFileWriter.close()

  def SaveGamestap(gameState: IGameStap): Unit =
    saveToFile(gameState.gplayer, "AvroImpl/player.avro")
    savePlayersToFile(gameState.gplayerlist)
    saveToAvro("AvroImpl/field.avro", gameState.gfield)

  def LoadGameStap(): IGameStap =
    val loadedField = loadFieldFromFile("AvroImpl/field.avro")
    val loadedPlayerList = loadPlayers
    val loadedPlayer = loadFromFile("AvroImpl/player.avro")
    given_IGameStap.newGamestap(loadedField, loadedPlayer, loadedPlayerList)

object PlayerConfigurator:
  val schemaString =
    """
      |{
      |  "type": "record",
      |  "name": "PlayerName",
      |  "fields": [
      |    {
      |      "name": "name",
      |      "type": "string"
      |    }
      |  ]
      |}
      |""".stripMargin

  private def saveToAvro(filePath: String, playerName: String): Unit =
    val schema = new Schema.Parser().parse(schemaString)
    val datum = new GenericData.Record(schema)
    datum.put("name", playerName)
    val datumWriter: GenericDatumWriter[GenericRecord] = new GenericDatumWriter[GenericRecord](schema)
    val dataFileWriter: DataFileWriter[GenericRecord] = new DataFileWriter[GenericRecord](datumWriter)
    dataFileWriter.create(schema, new File(filePath))
    dataFileWriter.append(datum)
    dataFileWriter.close()

  def savePlayerStrategy(player: IPlayerStrategy): Unit =
    PlayerConfigurator.saveToAvro("AvroImpl/playerstrategy.avro", player.getClass.getSimpleName)

  def loadPlayerStrategyName: IPlayerStrategy =
    val injector = IGameInjector.createInjector()
    val schema = new Schema.Parser().parse(schemaString)
    val datumReader: GenericDatumReader[GenericRecord] = new GenericDatumReader[GenericRecord](schema)
    val dataFileReader: DataFileReader[GenericRecord] = new DataFileReader[GenericRecord](new File("AvroImpl/playerstrategy.avro"), datumReader)
    val playerName: String = dataFileReader.next().get("name").toString
    dataFileReader.close()
    playerName match
      case "AIPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("AIPlayer")))
      case "HumanPlayer" => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
      case _ => injector.getInstance(Key.get(classOf[IPlayerStrategy], Names.named("HumanPlayer")))
