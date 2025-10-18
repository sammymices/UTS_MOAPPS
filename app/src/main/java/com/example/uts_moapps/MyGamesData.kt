package com.example.uts_moapps

import com.example.uts_moapps.model.GameModel

object MyGamesData {
    // pakai nama variabel asli supaya kode yang merujuk ke myGames tidak error
    private val myGames = mutableListOf<GameModel>()

    // menambahkan game (hindari duplikat) — _API lama tetap sama_
    fun addGame(game: GameModel) {
        if (!myGames.any { it.title == game.title }) {
            myGames.add(game)
        }
    }

    // ambil daftar (API lama tetap sama)
    fun getMyGames(): List<GameModel> = myGames

    // tambahan: menghapus game (refund)
    fun removeGame(game: GameModel) {
        myGames.removeAll { it.title == game.title }
    }

    // tambahan: cek kepemilikan
    fun isOwned(game: GameModel): Boolean = myGames.any { it.title == game.title }
}
