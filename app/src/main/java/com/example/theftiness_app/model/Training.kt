package com.example.theftiness_app.model

data class Training(
    val _id: String? = null,
    val textoOriginal: String,
    val metricas: Metricas,
    val fechaEntrenamiento: String,
    val horaRegistro: String,
    val timestamp: String
)

data class Metricas(
    val distanciaKm: Double,
    val duracionMin: Int,
    val calorias: Int = 0
)