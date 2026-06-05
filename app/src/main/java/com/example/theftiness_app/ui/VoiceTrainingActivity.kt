package com.example.theftiness_app.ui

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.theftiness_app.R
import com.example.theftiness_app.model.Metricas
import com.example.theftiness_app.model.Training
import com.example.theftiness_app.network.RetrofitClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VoiceTrainingActivity : AppCompatActivity() {

    private lateinit var etTexto: EditText
    private lateinit var etDistancia: EditText
    private lateinit var etDuracion: EditText

    private lateinit var btnVoice: Button
    private lateinit var btnGuardar: Button

    private val SPEECH_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_voice_training)

        etTexto = findViewById(R.id.etTexto)
        etDistancia = findViewById(R.id.etDistancia)
        etDuracion = findViewById(R.id.etDuracion)

        btnVoice = findViewById(R.id.btnVoice)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnVoice.setOnClickListener {

            try {
                iniciarReconocimientoVoz()
            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Error al iniciar reconocimiento de voz",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnGuardar.setOnClickListener {
            guardarEntrenamiento()
        }
    }

    private fun iniciarReconocimientoVoz() {

        try {

            val intent =
                Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "es-MX"
            )

            intent.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Describe tu entrenamiento"
            )

            startActivityForResult(
                intent,
                SPEECH_REQUEST_CODE
            )

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "Reconocimiento de voz no disponible",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == SPEECH_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK
        ) {

            val resultados =
                data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )

            if (!resultados.isNullOrEmpty()) {

                val textoReconocido = resultados[0]

                etTexto.setText(textoReconocido)

                extraerMetricas(textoReconocido)

                Toast.makeText(
                    this,
                    "Texto reconocido correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun extraerMetricas(texto: String) {

        try {

            val numeros =
                Regex("\\d+")
                    .findAll(texto)
                    .map { it.value }
                    .toList()

            if (numeros.isNotEmpty()) {
                etDistancia.setText(numeros[0])
            }

            if (numeros.size > 1) {
                etDuracion.setText(numeros[1])
            }

        } catch (_: Exception) {

        }
    }

    private fun hayInternet(): Boolean {

        val connectivityManager =
            getSystemService(
                CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        val network =
            connectivityManager.activeNetwork
                ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }

    private fun guardarEntrenamiento() {

        if (!hayInternet()) {

            Toast.makeText(
                this,
                "Sin conexión a internet",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val texto = etTexto.text.toString()

        val distancia =
            etDistancia.text.toString().toDoubleOrNull() ?: 0.0

        val duracion =
            etDuracion.text.toString().toIntOrNull() ?: 0

        val fecha =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Date())

        val hora =
            SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).format(Date())

        val timestamp =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()
            ).format(Date())

        val entrenamiento =
            Training(
                textoOriginal = texto,
                metricas = Metricas(
                    distanciaKm = distancia,
                    duracionMin = duracion
                ),
                fechaEntrenamiento = fecha,
                horaRegistro = hora,
                timestamp = timestamp
            )

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.apiService
                        .guardarEntrenamiento(
                            entrenamiento
                        )

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@VoiceTrainingActivity,
                        "Entrenamiento guardado correctamente",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        this@VoiceTrainingActivity,
                        "Error al guardar",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@VoiceTrainingActivity,
                    "No se pudo conectar al servidor",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}