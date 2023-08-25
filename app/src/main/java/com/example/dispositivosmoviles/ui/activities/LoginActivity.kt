package com.example.dispositivosmoviles.ui.activities

import android.content.Context
import android.content.Intent


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts.*

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.net.URI
import java.util.UUID


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(this, MenuPrincipalActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

    private fun recoveryPasswordWithEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Correo de recuperacio enviado correctamente",
                        Toast.LENGTH_SHORT,
                    ).show()

                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Alert")
                        setMessage("Correo de recuperacion enviado correctamente")
                        setCancelable(true)
                    }
                }

            }
    }

    override fun onStart() {
        super.onStart()

        binding.btnIngresar.setOnClickListener {

            if (binding.txtNombre.text.toString()
                    .isNotEmpty() && binding.txtContasena.text.toString().isNotEmpty()
            ) {
                signInWithEmailAndPassword(
                    binding.txtNombre.text.toString(),
                    binding.txtContasena.text.toString()
                )
            } else {
                Toast.makeText(
                    baseContext,
                    "Llene los campos.",
                    Toast.LENGTH_SHORT,
                ).show()
            }


        }

        binding.txtvwOlvidoPassword.setOnClickListener {
            if (binding.txtNombre.text.toString().isNotEmpty()) {
                recoveryPasswordWithEmail(
                    binding.txtNombre.text.toString()
                )
            } else {
                Toast.makeText(
                    baseContext,
                    "Ingrese su correo electronico en el campo",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        binding.txtvwRegistrarse.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
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
                    sn.setBackgroundTint(resources.getColor(R.color.rojo))
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

        binding.btnFacebook.setOnClickListener {
            val facebookUrl = "https://www.facebook.com/MarvelLatinoamerica/?brand_redir=6883542487"
            openUrlInBrowser(facebookUrl)
        }

        binding.btnTwitter.setOnClickListener {
            val twitterUrl = "https://twitter.com/Marvel?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
            openUrlInBrowser(twitterUrl)
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

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }



}