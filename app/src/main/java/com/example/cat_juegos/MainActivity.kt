package com.example.cat_juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.example.cat_juegos.adapter.GameAdapter
import com.example.cat_juegos.db.DbGames
import com.example.cat_juegos.model.Game
import com.example.cat_juegos.model.GameListViewModel



class MainActivity : AppCompatActivity() {

    private lateinit var listGames: ArrayList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbgames = DbGames(this)
        listGames = dbgames.getGames()
        val gameAdapter = GameAdapter(this, listGames)

        val listView: ListView = findViewById(R.id.lvGames)
        listView.adapter = gameAdapter

        listView.setOnItemClickListener { adapterView, view, i, l ->
            //l es el id
            //i es la posición
            val intent = Intent(this, GameDetailActivity::class.java)
            intent.putExtra(getString(R.string.GAME_ID), l.toInt())

            startActivity(intent)
            finish()
        }



    }


    fun click(view: View) {
        startActivity(Intent(this, GameInsertActivity::class.java))
        finish()
    }
}