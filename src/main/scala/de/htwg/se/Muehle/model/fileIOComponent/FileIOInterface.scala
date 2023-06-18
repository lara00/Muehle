package de.htwg.se.Muehle.model.fileIOComponent

import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.model.gameComponent.IGameStap

trait FileIOInterface {

    def load:(IGameStap, IPlayerStrategy)
    def save(g : IGameStap, p : IPlayerStrategy): Unit
}