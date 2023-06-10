package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.ActivityPrincipalBinding
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.google.android.material.snackbar.Snackbar

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
        /*intent.extras.let {
            name = it?.getString("var1")!!
        }*/

        //Log.d("UCE","Hola ${name}")
        binding.textName.text = "Bienvenido "+name.toString()

        //Log.d("UCE","Entrada a Start")

        binding.botonUno.setOnClickListener {
            //iniciar el objeto intent
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

        }


        binding.bottomNavigation.setOnItemSelectedListener() { item ->
            when(item.itemId) {
                R.id.inicio -> {

                    val frag = FirstFragment()                //instanciar fragment
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, frag)          //(contenedor, objeto) siempre debe estar en una activity
                    transaction.addToBackStack(null) //para crear pila
                    transaction.commit()

                    true
                }
                R.id.favoritos -> {
                    // Respond to navigation item 2 click
                    binding.textName.text = "Entramos a Favoritos"

                    var suma:Int = 0
                    for(i in listOf(8,12,13)){
                        suma += 1
                    }

                    // Respond to navigation item 1 click
                    Snackbar.make(binding.textName,"La suma es {$suma}", Snackbar.LENGTH_LONG).show()

                    true
                }
                R.id.apis -> {
                    // Respond to navigation item 2 click
                    binding.textName.text = "Entramos a Apis"
                    Snackbar.make(binding.textName,"Entramos a Apis", Snackbar.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }


}