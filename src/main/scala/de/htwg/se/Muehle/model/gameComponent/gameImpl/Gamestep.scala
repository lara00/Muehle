package de.htwg.se.Muehle.model.gameComponent.gameImpl

import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.MillEvents
import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.gameComponent.IGameStap

case class GameStap(field: Field, player: Player, playerlist: PlayerList) extends IGameStap:
  def gplayer:Player = player
  def gplayerlist: PlayerList = playerlist
  def playername: Stone = player.pname
  def gfield: Field = field
  def stonesofaktiveplayer: Int = player.stonetoput
  def getNextPlayer: Player = playerlist.getNextPlayer(player)

  private def updatedStonesInField: PlayerList = playerlist.updateStonesInField(player)

  private def updateafterMill: PlayerList = playerlist.updateStonesafterMill(getNextPlayer)

  private def delete_a_stone_is_possible(delete: Int): Boolean = getNextPlayer.name == field.fields(delete) 
    && !Mill(field).isMill(delete) && (playerlist.allowedtodeleteastone(player))
  
  def timetoSetMoveJumporMill(to:Int, from:Int): (IGameStap, MoveEvents) = 
    val newField = StoneMovement(player, field, to, from)
    (player.stonetoput, Mill(newField).isMill(to), newField != field, from) match 
      case (n, false, true, -1) if n != 0 => (SetStone(newField), MoveEvents.SetStone)
      case (n, true, true, -1) if n != 0 => (SetStone_Mill(newField),MoveEvents.SetStone_Mill)
      case (_, false, true,_) => (MoveStone(newField), MoveEvents.SetStone)
      case (_, true, true,_) => (GameStap(newField,player,playerlist), MoveEvents.MoveStone_Mill)
      case _ => (this, MoveEvents.NoMove)
    
  def handleMill(delete: Int): (IGameStap, MillEvents) =
    this match 
      case GameStap(field, player, playerlist) if(delete_a_stone_is_possible(delete)) =>
        (updateGameStapAfterDeleteStone(delete),MillEvents.DeleteStone)
      case GameStap(_, player, playerlist) if playerlist.threeStonesontheField(player) 
      && getNextPlayer.name == field.fields(delete)   =>
        (updateGameStapAfterDeleteStone(delete), MillEvents.EndGame)
      case _ => 
        (this, MillEvents.WrongDelete)
    
  private def SetStone(newfield: Field): IGameStap =
      GameStap(newfield, getNextPlayer, updatedStonesInField)

  private def MoveStone(newfield: Field): IGameStap =  
      GameStap(newfield, getNextPlayer, playerlist)

  private def SetStone_Mill(newfield: Field): IGameStap =
      val updatedPlayerList = playerlist.updateStonesInField(player)
      GameStap(newfield,player.incrementStoneintheField.stonetoputinthefield,updatedPlayerList)

  private def updateGameStapAfterDeleteStone(delete: Int): IGameStap =
    val newfield = field.deleteStone(delete, getNextPlayer.name)
    GameStap(newfield, getNextPlayer.decrementStoneintheField, updateafterMill)
