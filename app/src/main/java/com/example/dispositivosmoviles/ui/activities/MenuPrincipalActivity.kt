package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMenuPrincipalBinding
import com.example.dispositivosmoviles.ui.utilities.MyLocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class MenuPrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuPrincipalBinding

    //Ubicacion y GPS
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback
    private lateinit var client : SettingsClient
    private lateinit var locationSettingRequest : LocationSettingsRequest

    private var currentLocation: Location? = null


    @SuppressLint("MissingPermission")
    private val locationContract = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        when (isGranted == true) {
            true -> {

                client.checkLocationSettings(locationSettingRequest).apply {
                    addOnSuccessListener {
                        val task = fusedLocationProviderClient.lastLocation
                        task.addOnSuccessListener { location ->
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallBack,
                                Looper.getMainLooper()
                            )
                        }
                    }
                    addOnFailureListener{ex ->
                        if(ex is ResolvableApiException){
                            //startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                            ex.startResolutionForResult(
                                this@MenuPrincipalActivity,
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                            )
                        }

                    }
                }


            }

            shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                Snackbar.make(
                    binding.titleTextView,
                    "Ayude con el permiso porfa",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            false -> {
            }

            else -> {
                Snackbar.make(binding.titleTextView, "Permiso Denegado", Snackbar.LENGTH_LONG).show()
            }
        }

    }

    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(
                binding.titleTextView, "",
                Snackbar.LENGTH_LONG
            )
            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val msg = activityResult
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        .toString()

                    if (msg.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        Log.d("UCE", msg)
                        intent.putExtra(SearchManager.QUERY, msg.toString())
                        startActivity(intent)
                    }
                }

                RESULT_CANCELED -> {
                    message = "Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.rojo))
                }

                else -> {
                    message = "Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.rojo))
                }
            }

            sn.setText(message)
            sn.show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        )
            .setMaxUpdates(1)
            .build()

        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {
                    locationResult.locations.forEach { location ->
                        currentLocation = location
                        Log.d(
                            "UCE", "Ubicacion: ${location.latitude}," +
                                    "${location.longitude}"
                        )

                        Snackbar.make(
                            binding.titleTextView,
                            "Ubicacion: ${location.latitude}," +
                                    "${location.longitude}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.d("UCE", "GPS apagado")
                }
            }
        }
        client = LocationServices.getSettingsClient(this)

        locationSettingRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()


    }

    override fun onStart() {
        super.onStart()

        binding.btnMarvel.setOnClickListener {
            startActivity(Intent(this, BiometricActivity::class.java))
        }

        binding.btnCamara.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        binding.btnNotificacion.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }


        binding.btnInternet.setOnClickListener {
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

        binding.btnLocalizacion.setOnClickListener {
            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding.btnSalir.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
    }

    private fun test(){
        var location = MyLocationManager(this)
        location.getUserLocation()
    }
}