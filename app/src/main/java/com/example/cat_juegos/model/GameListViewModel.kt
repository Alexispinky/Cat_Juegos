package com.example.cat_juegos.model

import androidx.lifecycle.ViewModel
import com.example.cat_juegos.db.DbGames
import kotlin.random.Random


class GameListViewModel(val dataSource: DbGames) : ViewModel() {
    val gameLiveData = dataSource.getGames()

    fun insertGame(title: String, genre: String, developer: String, player: Int){

        dataSource.insertGame(title,genre,developer,player)
    }
}