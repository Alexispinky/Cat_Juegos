package com.example.cat_juegos.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.media.AsyncPlayer
import com.example.cat_juegos.model.Game

class DbGames(context: Context) :DBHelper(context) {

    val context = context

    fun insertGame(title: String, genre: String, developer: String, player: Int): Long{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0

        try{

            val values = ContentValues()

            values.put("title", title)
            values.put("genre", genre)
            values.put("developer", developer)
            values.put("player",player)

            id = db.insert(TABLE_GAMES, null, values)

        }catch(e: Exception){
            //Manejo de la excepción
        }finally {
            db.close()
        }

        return id
    }

    fun getGames(): ArrayList<Game>{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var listGames = ArrayList<Game>()
        var gameTmp: Game? = null
        var cursorGames: Cursor? = null

        cursorGames = db.rawQuery("SELECT * FROM $TABLE_GAMES", null)

        if(cursorGames.moveToFirst()){
            do{
                gameTmp = Game(cursorGames.getInt(0), cursorGames.getString(1), cursorGames.getString(2), cursorGames.getString(3),cursorGames.getInt(4))
                listGames.add(gameTmp)
            }while(cursorGames.moveToNext())
        }

        cursorGames.close()

        return listGames
    }

    fun getGame(id: Int): Game?{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var game: Game? = null
        var cursorGames: Cursor? = null

        cursorGames = db.rawQuery("SELECT * FROM $TABLE_GAMES WHERE id = $id LIMIT 1", null)

        if(cursorGames.moveToFirst()){
            game = Game(cursorGames.getInt(0), cursorGames.getString(1), cursorGames.getString(2), cursorGames.getString(3),cursorGames.getInt(4))
        }

        cursorGames.close()

        return game
    }

    fun updateGame(id: Int, title: String, genre: String, developer: String, player: Int): Boolean{

        var banderaCorrecto = false

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE $TABLE_GAMES SET title = '$title', genre = '$genre', developer = '$developer', player = '$player' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto

    }

    fun deleteGame(id: Int): Boolean{

        var banderaCorrecto = true

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM $TABLE_GAMES WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }
}