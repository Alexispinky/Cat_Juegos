package com.example.cat_juegos

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cat_juegos.databinding.ActivityGameDetailBinding
import com.example.cat_juegos.db.DbGames
import com.example.cat_juegos.model.Game

class GameDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameDetailBinding

    private lateinit var dbGames: DbGames
    var game: Game? = null
    var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
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

                //Para que no se abra el teclado al momento de hacer click en los TextInputEditText
                tietTitulo.inputType = InputType.TYPE_NULL
                //tietGenre.inputType = InputType.TYPE_NULL
                tietDeveloper.inputType = InputType.TYPE_NULL

            }
        }
    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit -> {
                val intent = Intent(this, GameEditActivity::class.java)
                intent.putExtra(getString(R.string.GAME_ID), id)
                startActivity(intent)
                finish()
            }

            R.id.btnDelete -> {
                AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Realmente deseas eliminar el juego ${game?.name}?")
                    .setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface, i ->
                        if(dbGames.deleteGame(id)){
                            Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        //Si se desea realizar alguna acción cuando el usuario selecciona NO
                    })
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}