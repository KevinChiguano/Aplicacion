package com.example.dispositivosmoviles.ui.activities


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


import androidx.lifecycle.lifecycleScope

import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityLoginBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnIngresar.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtNombre.text.toString(),
                binding.txtContasena.text.toString()
            )

            if (check) {

                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.txtNombre.text.toString())
                }

                var intent = Intent(
                    this,
                    PrincipalActivity::class.java
                )

                intent.putExtra("var1", binding.txtNombre.text.toString())

                startActivity(intent)


            } else {
                Snackbar.make(
                    binding.txtNombre,
                    "Usuario o contraseña invalidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

        binding.btnSearch.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW,
//                //Uri.parse("https://www.google.com.ec/maps")
//                Uri.parse("tel:0123456789")
//            )

            val intent = Intent(
                Intent.ACTION_WEB_SEARCH
            )
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(SearchManager.QUERY, "Anime")
            startActivity(intent)
        }

        val appResultLocal = registerForActivityResult(StartActivityForResult()) { resultActivity ->

            //contrato
            when (resultActivity.resultCode) {
                RESULT_OK -> {
                    Snackbar.make(
                        binding.textView, "Resultado exitos",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                RESULT_CANCELED -> {
                    Snackbar.make(
                        binding.textView, "Resultado fallido",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                else -> {
                    Snackbar.make(
                        binding.textView, "Resultado extraño",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

        }

        binding.btnResult.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)

        }

    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dimoviles@uce.edu.ec"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}