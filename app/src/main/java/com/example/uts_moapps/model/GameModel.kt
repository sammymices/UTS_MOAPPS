package com.example.uts_moapps.model

import java.io.Serializable

data class GameModel(
    val title: String,
    val price: String,
    val imageResId: Int,
    val developer: String = "",
    val publisher: String = "",
    val releaseDate: String = "",
    val description: String = "",
    val genres: List<String> = emptyList(),
    val reviews: Int = 0
) : Serializable
