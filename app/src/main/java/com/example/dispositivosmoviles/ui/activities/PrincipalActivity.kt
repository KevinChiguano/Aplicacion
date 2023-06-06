package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        Log.d("UCE","Entrada a Create")
        //Inicializa el binding
        binding = ActivityPrincipalBinding.inflate(layoutInflater)

        //Poner los objetos en la clase
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()

        var name : String = ""

        //funcion let{}
        //let -> descompone un objeto e itera similar a  "While"
        //apply -> coger un boton y aplicar diferentes cosas
        //with -> transformar
        intent.extras.let {
            name = it?.getString("var1")!!
        }

        Log.d("UCE","Hola ${name}")
        binding.textName.text = "Bienvenido "+name.toString()

        Log.d("UCE","Entrada a Start")

        binding.botonUno.setOnClickListener {
            //iniciar el objeto intent
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}