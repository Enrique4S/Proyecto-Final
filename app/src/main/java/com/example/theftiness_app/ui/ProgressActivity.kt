package com.example.theftiness_app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.theftiness_app.R
import com.example.theftiness_app.model.Training
import com.example.theftiness_app.network.RetrofitClient
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_progress)

        lineChart = findViewById(R.id.lineChart)

        cargarEntrenamientos()
    }

    private fun cargarEntrenamientos() {

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.apiService
                        .getEntrenamientos()

                if (response.isSuccessful) {

                    val entrenamientos =
                        response.body() ?: emptyList()

                    mostrarGrafica(entrenamientos)

                } else {

                    Toast.makeText(
                        this@ProgressActivity,
                        "No se pudieron obtener datos",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@ProgressActivity,
                    "Esperando conexión con servidor",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun mostrarGrafica(
        entrenamientos: List<Training>
    ) {

        val entries = ArrayList<Entry>()

        entrenamientos.forEachIndexed { index, entrenamiento ->

            entries.add(
                Entry(
                    index.toFloat(),
                    entrenamiento.metricas.distanciaKm.toFloat()
                )
            )
        }

        val dataSet =
            LineDataSet(
                entries,
                "Kilómetros recorridos"
            )

        val lineData =
            LineData(dataSet)

        lineChart.data = lineData

        lineChart.invalidate()
    }
}