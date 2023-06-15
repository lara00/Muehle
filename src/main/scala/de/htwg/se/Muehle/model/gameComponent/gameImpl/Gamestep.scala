package de.htwg.se.Muehle.model.gameComponent.gameImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.{MillEvents, MoveEvents, PlayerList, Stone}

case class GameStap @Inject()(field: IField, player: IPlayer, playerlist: PlayerList) extends IGameStap:
  def newGamestap(field: IField, player: IPlayer, playerlist: PlayerList) = GameStap(field, player,playerlist)
  def gplayer: IPlayer = player
  def gplayerlist: PlayerList = playerlist
  def playername: Stone = player.pname
  def gfield: IField = field
  def stonesofaktiveplayer: Int = player.pstonetoput
  def getNextPlayer: IPlayer = playerlist.getNextPlayer(player)

  private def updatedStonesInField: PlayerList = playerlist.updateStonesInField(player)

  private def updateafterMill: PlayerList = playerlist.updateStonesafterMill(getNextPlayer)

  private def delete_a_stone_is_possible(delete: Int): Boolean = getNextPlayer.pname == field.stones_field(delete) 
    && !Mill(field).isMill(delete) && (playerlist.allowedtodeleteastone(player))
  
  def timetoSetMoveJumporMill(to:Int, from:Int): (IGameStap, MoveEvents) = 
    val newField = StoneMovement(player, field, to, from)
    (player.pstonetoput, Mill(newField).isMill(to), newField != field, from) match 
      case (n, false, true, -1) if n != 0 => (SetStone(newField), MoveEvents.SetStone)
      case (n, true, true, -1) if n != 0 => (SetStone_Mill(newField),MoveEvents.SetStone_Mill)
      case (_, false, true,_) => (MoveStone(newField), MoveEvents.MoveStone)
      case (_, true, true,_) => (GameStap(newField,player,playerlist), MoveEvents.MoveStone_Mill)
      case _ => (this, MoveEvents.NoMove)
    
  def handleMill(delete: Int): (IGameStap, MillEvents) =
    this match 
      case GameStap(field, player, playerlist) if(delete_a_stone_is_possible(delete)) =>
        (updateGameStapAfterDeleteStone(delete),MillEvents.DeleteStone)
      case GameStap(_, player, playerlist) if playerlist.threeStonesontheField(player) 
      && getNextPlayer.pname == field.stones_field(delete)   =>
        (updateGameStapAfterDeleteStone(delete), MillEvents.EndGame)
      case _ => 
        (this, MillEvents.WrongDelete)
    
  private def SetStone(newfield: IField): IGameStap = GameStap(newfield, getNextPlayer, updatedStonesInField)

  private def MoveStone(newfield: IField): IGameStap = GameStap(newfield, getNextPlayer, playerlist)

  private def SetStone_Mill(newfield: IField): IGameStap =
      val updatedPlayerList = playerlist.updateStonesInField(player)
      GameStap(newfield,player.incrementStoneintheField.stonetoputinthefield,updatedPlayerList)

  private def updateGameStapAfterDeleteStone(delete: Int): IGameStap =
    val newfield = field.deleteStone(delete, getNextPlayer.pname)
    GameStap(newfield, getNextPlayer.decrementStoneintheField, updateafterMill)