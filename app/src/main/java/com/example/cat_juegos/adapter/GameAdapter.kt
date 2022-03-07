package com.example.cat_juegos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.res.TypedArrayUtils.getString
import com.example.cat_juegos.R
import com.example.cat_juegos.R.*
import com.example.cat_juegos.databinding.ItemBinding
import com.example.cat_juegos.model.Game

class GameAdapter(contexto: Context, listGames: ArrayList<Game>): BaseAdapter() {

    private val listGames = listGames
    private val layoutInflater = LayoutInflater.from(contexto)

    override fun getCount(): Int {
        return listGames.size
    }

    override fun getItem(p0: Int): Any {
        return listGames[p0]
    }

    override fun getItemId(p0: Int): Long {
        return listGames[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = ItemBinding.inflate(layoutInflater)
        with(binding){
            tvTitle.text = listGames[p0].name
            tvDeveloper.text = listGames[p0].developer
            tvPlayer.text = listGames[p0].players.toString()
            tvGenero.text = listGames[p0].genre
            when(listGames[p0].genre){
                "Accion" -> ivHead.setImageResource(drawable.action)
                "Aventura" -> ivHead.setImageResource(drawable.adventure)
                "Peleas" -> ivHead.setImageResource(drawable.fight)
                //tuve que harcodear los tipos del genero ya que el codigo de abajo mostraba error
                // getString(R.string.Fight) -> ivHead.setImageResource(drawable.fight)
            }
        }

        return binding.root
    }
}
