package com.example.uts_moapps.model

import com.example.uts_moapps.R

object GameData {

    // 🔹 Daftar game untuk "Top Picks"
    val topGames = listOf(
        GameModel(
            title = "Cyberpunk 2067",
            price = "Rp 250.000",
            imageResId = R.drawable.cyberpunk2,
            developer = "CD Projekt Red",
            publisher = "CD Projekt",
            releaseDate = "10 Desember 2020",
            description = "Open-world RPG futuristik dengan tema dystopian dan aksi intens.",
            genres = listOf("Action", "RPG", "Open World"),
            reviews = 128000
        ),
        GameModel(
            title = "GTA V",
            price = "Rp 150.000",
            imageResId = R.drawable.gta,
            developer = "Rockstar North",
            publisher = "Rockstar Games",
            releaseDate = "17 September 2013",
            description = "Kisah tiga kriminal di kota fiksi Los Santos yang penuh aksi.",
            genres = listOf("Action", "Adventure", "Open World"),
            reviews = 255000
        ),
        GameModel(
            title = "The Witcher 3: Wild Hunt",
            price = "Rp 200.000",
            imageResId = R.drawable.witcher,
            developer = "CD Projekt Red",
            publisher = "CD Projekt",
            releaseDate = "19 Mei 2015",
            description = "Petualangan epik Geralt dalam dunia penuh monster dan intrik politik.",
            genres = listOf("RPG", "Fantasy", "Adventure"),
            reviews = 300000
        ),
        GameModel(
            title = "Elden Ring",
            price = "Rp 550.000",
            imageResId = R.drawable.elden,
            developer = "FromSoftware",
            publisher = "Bandai Namco",
            releaseDate = "25 Februari 2022",
            description = "Game souls-like dengan dunia open-world luas dan penuh misteri.",
            genres = listOf("RPG", "Action", "Fantasy"),
            reviews = 410000
        ),
        GameModel(
            title = "Hades",
            price = "Rp 120.000",
            imageResId = R.drawable.hades,
            developer = "Supergiant Games",
            publisher = "Supergiant Games",
            releaseDate = "17 September 2020",
            description = "Game roguelike penuh aksi di dunia bawah mitologi Yunani.",
            genres = listOf("Action", "Roguelike", "Indie"),
            reviews = 200000
        )
    )

    // 🔹 Daftar game untuk "Recommendations"
    val recommendedGames = listOf(
        GameModel(
            title = "Dying Light 2",
            price = "Rp 350.000",
            imageResId = R.drawable.dying,
            developer = "Techland",
            publisher = "Techland",
            releaseDate = "4 Februari 2022",
            description = "Game survival parkour dengan latar dunia pasca-apokaliptik.",
            genres = listOf("Action", "Survival", "Adventure"),
            reviews = 95000
        ),
        GameModel(
            title = "Resident Evil 4 Remake",
            price = "Rp 450.000",
            imageResId = R.drawable.re4,
            developer = "Capcom",
            publisher = "Capcom",
            releaseDate = "24 Maret 2023",
            description = "Remake modern dari game klasik survival horror legendaris.",
            genres = listOf("Horror", "Action", "Adventure"),
            reviews = 160000
        ),
        GameModel(
            title = "Ghost of Tsushima",
            price = "Rp 600.000",
            imageResId = R.drawable.ghost,
            developer = "Sucker Punch Productions",
            publisher = "Sony Interactive Entertainment",
            releaseDate = "17 Juli 2020",
            description = "Petualangan samurai epik di Jepang feodal dengan visual memukau dan sistem pertarungan halus.",
            genres = listOf("Action", "Adventure", "Open World"),
            reviews = 220000
        ),
        GameModel(
            title = "Sekiro: Shadows Die Twice",
            price = "Rp 550.000",
            imageResId = R.drawable.sekiro,
            developer = "FromSoftware",
            publisher = "Activision",
            releaseDate = "22 Maret 2019",
            description = "Game aksi dengan fokus pada refleks cepat, stealth, dan pertarungan pedang yang intens.",
            genres = listOf("Action", "Adventure", "Souls-like"),
            reviews = 180000
        ),
        GameModel(
            title = "Hollow Knight",
            price = "Rp 150.000",
            imageResId = R.drawable.hollow,
            developer = "Team Cherry",
            publisher = "Team Cherry",
            releaseDate = "24 Februari 2017",
            description = "Metroidvania dengan dunia bawah tanah yang misterius dan gaya seni indah nan gelap.",
            genres = listOf("Action", "Platformer", "Adventure"),
            reviews = 310000
        ),
        GameModel(
            title = "Red Dead Redemption 2",
            price = "Rp 700.000",
            imageResId = R.drawable.reddead,
            developer = "Rockstar Games",
            publisher = "Rockstar Games",
            releaseDate = "26 Oktober 2018",
            description = "Kisah epik di era koboi Amerika dengan dunia terbuka paling detail yang pernah dibuat.",
            genres = listOf("Action", "Adventure", "Open World"),
            reviews = 500000
        ),
        GameModel(
            title = "Stardew Valley",
            price = "Rp 120.000",
            imageResId = R.drawable.stardew,
            developer = "ConcernedApe",
            publisher = "ConcernedApe",
            releaseDate = "26 Februari 2016",
            description = "Game simulasi pertanian yang santai dengan elemen eksplorasi dan hubungan sosial.",
            genres = listOf("Simulation", "RPG", "Casual"),
            reviews = 420000
        ),
        GameModel(
            title = "Baldur's Gate 3",
            price = "Rp 850.000",
            imageResId = R.drawable.baldursgate,
            developer = "Larian Studios",
            publisher = "Larian Studios",
            releaseDate = "3 Agustus 2023",
            description = "RPG berbasis D&D dengan cerita mendalam, sistem pilihan kompleks, dan pertarungan turn-based strategis.",
            genres = listOf("RPG", "Adventure", "Fantasy"),
            reviews = 390000
        ),
        GameModel(
            title = "Alan Wake 2",
            price = "Rp 750.000",
            imageResId = R.drawable.alan,
            developer = "Remedy Entertainment",
            publisher = "Epic Games Publishing",
            releaseDate = "27 Oktober 2023",
            description = "Game horor psikologis dengan cerita gelap dan atmosfer sinematik luar biasa.",
            genres = listOf("Horror", "Thriller", "Adventure"),
            reviews = 145000
        ),
        GameModel(
            title = "Monster Hunter: World",
            price = "Rp 400.000",
            imageResId = R.drawable.monster,
            developer = "Capcom",
            publisher = "Capcom",
            releaseDate = "26 Januari 2018",
            description = "Berburu monster raksasa di dunia terbuka luas dengan sistem senjata dan armor yang mendalam.",
            genres = listOf("Action", "RPG", "Co-op"),
            reviews = 270000
        )
    )
}
