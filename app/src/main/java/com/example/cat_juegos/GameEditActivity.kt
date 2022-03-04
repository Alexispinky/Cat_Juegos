package com.example.cat_juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.example.cat_juegos.databinding.ActivityGameEditBinding
import com.example.cat_juegos.db.DbGames
import com.example.cat_juegos.model.Game

class GameEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameEditBinding

    private lateinit var dbGames: DbGames
    var game: Game? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val bundle = intent.extras
            if(bundle != null){
                id = bundle.getInt(getString(R.string.GAME_ID), 0)
            }
        }else{
            id = savedInstanceState.getSerializable(getString(R.string.GAME_ID)) as Int
        }

        dbGames = DbGames(this)

        game = dbGames.getGame(id)

        if(game != null){
            with(binding){
                tietTitulo.setText(game?.name)
                //tietGenre.setText(game?.genre)
                tietDeveloper.setText(game?.developer)

            }
        }

    }

    fun click(view: View) {
        with(binding){
            if(!tietTitulo.text.toString().isEmpty()  && !tietDeveloper.text.toString().isEmpty() && !tietPlayer.text.toString().isEmpty()){
                if(dbGames.updateGame(id, tietTitulo.text.toString(), "0", tietDeveloper.text.toString(),tietPlayer.text.toString().toInt())){
                    Toast.makeText(this@GameEditActivity, "Registro actualizado exitosamente", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@GameEditActivity, GameDetailActivity::class.java)
                    intent.putExtra(getString(R.string.GAME_ID), id)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@GameEditActivity, "Error al actualizar el registro", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@GameEditActivity, "Ningún campo puede quedar vacío", Toast.LENGTH_LONG).show()
            }
        }

    }
}