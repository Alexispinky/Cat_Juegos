package com.example.cat_juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import com.example.cat_juegos.databinding.ActivityGameEditBinding
import com.example.cat_juegos.db.DbGames
import com.example.cat_juegos.model.Game

class GameEditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityGameEditBinding

    private lateinit var dbGames: DbGames
    var game: Game? = null
    var id: Int = 0
    private var posg : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.Generes, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this@GameEditActivity

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
                tietPlayer.setText(game?.players.toString())

            }
        }

    }

    fun click(view: View) {
        with(binding){

            when {
                tietTitulo.text.toString().isEmpty() -> tietTitulo.error =
                    getString(R.string.ErrorVacio)
                tietDeveloper.text.toString().isEmpty() -> tietDeveloper.error =
                    getString(R.string.ErrorVacio)
                tietPlayer.text.toString().isEmpty() -> tietPlayer.error =
                    getString(R.string.ErrorVacio)
                posg == 0 -> Toast.makeText(this@GameEditActivity,getString(R.string.ErrorSpinner),Toast.LENGTH_SHORT).show()
                else -> {
                    if(dbGames.updateGame(id, tietTitulo.text.toString(),spinner.getItemAtPosition(posg).toString(), tietDeveloper.text.toString(),tietPlayer.text.toString().toInt())){
                        Toast.makeText(this@GameEditActivity, getString(R.string.ActualizadoE), Toast.LENGTH_LONG).show()
                        val intent = Intent(this@GameEditActivity, GameDetailActivity::class.java)
                        intent.putExtra(getString(R.string.GAME_ID), id)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@GameEditActivity, getString(R.string.ErrorA), Toast.LENGTH_LONG).show()
                    }

                }
            }

            /*if(!tietTitulo.text.toString().isEmpty()  && !tietDeveloper.text.toString().isEmpty() && !tietPlayer.text.toString().isEmpty()){
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
            }*/
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when(p2)
        {
            1 -> {
                binding.ivHeader.setImageResource(R.drawable.action)
                posg = 1
            }
            2 -> {
                binding.ivHeader.setImageResource(R.drawable.adventure)
                posg = 2
            }
            3 -> {
                binding.ivHeader.setImageResource(R.drawable.fight)
                posg = 3
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        
    }
}