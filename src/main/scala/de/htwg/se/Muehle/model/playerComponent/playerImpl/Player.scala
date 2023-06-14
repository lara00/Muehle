package de.htwg.se.Muehle.model.playerComponent.playerImpl

import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.{PlayerList, Stone}

case class Player(name: Stone, stonetoput: Int, stoneintheField: Int) extends IPlayer :
  def pname: Stone = name
  def pstonetoput: Int = stonetoput
  def pstoneinField: Int = stoneintheField
  def pplayer(name: Stone, stonetoput: Int, stoneintheField: Int): IPlayer = Player(name,stonetoput, stoneintheField)
  def pplayerList(input:Int): PlayerList = PlayerList(List(new Player(Stone.White, input, 0), new Player(Stone.Black, input, 0)))
  def stonetoputinthefield: IPlayer = this.copy(stonetoput = stonetoput - 1)

  def incrementStoneintheField: IPlayer = this.copy(stoneintheField = stoneintheField + 1)

  def decrementStoneintheField: IPlayer = this.copy(stoneintheField = stoneintheField - 1)

  def setstone(field: IField, aktiveplayer: IPlayer, fieldenummber: Int): IField = field.setStone(fieldenummber, aktiveplayer.pname)