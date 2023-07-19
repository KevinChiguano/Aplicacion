package com.example.dispositivosmoviles.ui.activities


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
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
import java.util.Locale
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
            intent.putExtra(SearchManager.QUERY, "Anime Fenix")
            startActivity(intent)
        }

        val appResultLocal = registerForActivityResult(StartActivityForResult()) { resultActivity ->

            val sn = Snackbar.make(
                binding.textView, "",
                Snackbar.LENGTH_LONG
            )

            //contrato
            var message = when (resultActivity.resultCode) {
                RESULT_OK -> {
                    sn.setBackgroundTint(resources.getColor(R.color.blue))
                    resultActivity.data?.getStringExtra("result")
                        .orEmpty()
                }

                RESULT_CANCELED -> {
                    sn.setBackgroundTint(resources.getColor(R.color.naranja))
                    resultActivity.data?.getStringExtra("result")
                        .orEmpty()
                }

                else -> {
                    "Dudoso"
                }
            }

            sn.setText(message)
            sn.show()

        }

        val speechToText = registerForActivityResult(StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(
                binding.textView, "",
                Snackbar.LENGTH_LONG
            )
            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val msg = activityResult
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        .toString()

                    if(msg.isNotEmpty()){
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        Log.d("UCE",msg)
                        intent.putExtra(SearchManager.QUERY, msg.toString())
                        startActivity(intent)
                    }
                }
                RESULT_CANCELED -> {
                    message = "Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.naranja))
                }

                else -> {
                    message = "Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.naranja))
                }
            }

            sn.setText(message)
            sn.show()
        }

        binding.btnResult.setOnClickListener {
//            val resIntent = Intent(this, ResultActivity::class.java)
//            appResultLocal.launch(resIntent)

            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM

            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Di algo....."
            )
            speechToText.launch(intentSpeech)

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