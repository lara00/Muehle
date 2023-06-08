package de.htwg.se.Muehle.model

case class GameStap(field: Field, player: Player, playerlist: PlayerList):
  def getNextPlayer: Player = playerlist.getNextPlayer(player)

  private def updatedStonesInField: PlayerList = playerlist.updateStonesInField(player)

  private def updateafterMill: PlayerList = playerlist.updateStonesafterMill(getNextPlayer)

  private def delete_a_stone_is_possible(delete: Int): Boolean = getNextPlayer.name == field.fields(delete) 
    && !Mill(field).isMill(delete) && (playerlist.allowedtodeleteastone(player))
  
  def timetoSetMoveJumporMill(to:Int, from:Int): (GameStap, MoveEvents) = 
    val newField = StoneMovement(player, field, to, from)
    (player.stonetoput, Mill(newField).isMill(to), newField != field, from) match 
      case (n, false, true, -1) if n != 0 => (SetStone(newField), MoveEvents.SetStone)
      case (n, true, true, -1) if n != 0 => (SetStone_Mill(newField),MoveEvents.SetStone_Mill)
      case (_, false, true,_) => (MoveStone(newField), MoveEvents.SetStone)
      case (_, true, true,_) => (GameStap(newField,player,playerlist), MoveEvents.MoveStone_Mill)
      case _ => (this, MoveEvents.NoMove)
    
  def handleMill(delete: Int): (GameStap, MillEvents) =
    MillList.add_elementint(delete)
    this match 
      case GameStap(field, player, playerlist) if(delete_a_stone_is_possible(delete)) =>
        (updateGameStapAfterDeleteStone(delete),MillEvents.DeleteStone)
      case GameStap(_, player, playerlist) if playerlist.threeStonesontheField(player) 
      && getNextPlayer.name == field.fields(delete)   =>
        (updateGameStapAfterDeleteStone(delete), MillEvents.EndGame)
      case _ => 
        MillList.deleteElement()
        (this, MillEvents.WrongDelete)
    
  private def SetStone(newfield:Field): GameStap =
      GameStap(newfield, getNextPlayer, updatedStonesInField)

  private def MoveStone(newfield:Field): GameStap =  
      GameStap(newfield, getNextPlayer, playerlist)

  private def SetStone_Mill(newfield:Field): GameStap =
      val updatedPlayerList = playerlist.updateStonesInField(player)
      GameStap(newfield,player.incrementStoneintheField.stonetoputinthefield,updatedPlayerList)

  private def updateGameStapAfterDeleteStone(delete: Int): GameStap =
    val newfield = field.deleteStone(delete, getNextPlayer.name)
    GameStap(newfield, getNextPlayer.decrementStoneintheField, updateafterMill)
