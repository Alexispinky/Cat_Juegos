package com.example.cat_juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.cat_juegos.databinding.ActivityGameDetailBinding
import com.example.cat_juegos.databinding.ActivityGameInsertBinding
import com.example.cat_juegos.db.DbGames

class GameInsertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun click(view: View) {
        val dbGames = DbGames(this)

        with(binding){

            if(!tietTitulo.text.toString().isEmpty()  && !tietDeveloper.text.toString().isEmpty()){
                val id = dbGames.insertGame(tietTitulo.text.toString(), "0", tietDeveloper.text.toString(),tietPlayer.text.toString().toInt())

                if(id > 0) { //el registro se insertó correctamente
                    Toast.makeText(this@GameInsertActivity, "Registro guardado exitosamente", Toast.LENGTH_LONG).show()

                    //Reiniciamos las cajas de texto
                    tietTitulo.setText("")
                    //tietGenre.setText("")
                    tietDeveloper.setText("")
                    tietTitulo.requestFocus()
                }else{
                    Toast.makeText(this@GameInsertActivity, "Error al guardar el registro", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@GameInsertActivity, "Por favor llene todos los campos", Toast.LENGTH_LONG).show()

                //Para mandar un error en una caja de texto especíica
                //tietTitulo.text = "Por favor agrega un título"
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}