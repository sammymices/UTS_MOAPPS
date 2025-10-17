package com.example.uts_moapps

import com.example.uts_moapps.model.GameModel

object MyGamesData {
    private val myGames = mutableListOf<GameModel>()

    fun addGame(game: GameModel) {
        if (!myGames.any { it.title == game.title }) { // hindari duplikat
            myGames.add(game)
        }
    }

    fun getMyGames(): List<GameModel> = myGames
}
