package de.htwg.se.Muehle.model.playerComponent

import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.{PlayerList, Stone}

trait IPlayer {
  def pname: Stone
  def pstonetoput: Int
  def pstoneinField: Int
  def stonetoputinthefield: IPlayer
  def incrementStoneintheField: IPlayer
  def decrementStoneintheField: IPlayer
  def pplayerList(input:Int): PlayerList
  def setstone(field: IField, aktiveplayer: IPlayer, fieldenummber: Int): IField
  def pplayer(name: Stone, stonetoput: Int, stoneintheField: Int): IPlayer
}
