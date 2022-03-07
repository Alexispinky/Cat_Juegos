package com.example.cat_juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.cat_juegos.databinding.ActivityGameDetailBinding
import com.example.cat_juegos.databinding.ActivityGameInsertBinding
import com.example.cat_juegos.db.DbGames

class GameInsertActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityGameInsertBinding

    private var posg : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.Generes, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

    fun click(view: View) {
        val dbGames = DbGames(this)

        with(binding){

            when {
                tietTitulo.text.toString().isEmpty() -> tietTitulo.error =
                    getString(R.string.ErrorVacio)
                tietDeveloper.text.toString().isEmpty() -> tietDeveloper.error =
                    getString(R.string.ErrorVacio)
                tietPlayer.text.toString().isEmpty() -> tietPlayer.error =
                    getString(R.string.ErrorVacio)
                posg == 0 -> Toast.makeText(this@GameInsertActivity,getString(R.string.ErrorSpinner),Toast.LENGTH_SHORT).show()
                else -> {
                    val id = dbGames.insertGame(
                        tietTitulo.text.toString(),
                        spinner.getItemAtPosition(posg).toString(),
                        tietDeveloper.text.toString(),
                        tietPlayer.text.toString().toInt()
                    )

                    if (id > 0) { //el registro se insertó correctamente
                        Toast.makeText(
                            this@GameInsertActivity,
                            getString(R.string.GuardaE),
                            Toast.LENGTH_LONG
                        ).show()

                        //Reiniciamos las cajas de texto
                        tietTitulo.setText("")
                        tietDeveloper.setText("")
                        tietPlayer.setText("")
                        posg = 0
                        tietTitulo.requestFocus()
                    } else {
                        Toast.makeText(
                            this@GameInsertActivity,
                            getString(R.string.ErrorG),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                }
            }


            /*if(!tietTitulo.text.toString().isEmpty()  && !tietDeveloper.text.toString().isEmpty()){
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
            }*/



    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
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
        Toast.makeText(this@GameInsertActivity,"Selecciona algo",Toast.LENGTH_SHORT).show()
    }
}