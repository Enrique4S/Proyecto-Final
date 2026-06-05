package com.example.theftiness_app.network

import com.example.theftiness_app.model.Training
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("api/entrenamientos")
    suspend fun getEntrenamientos(): Response<List<Training>>

    @POST("api/entrenamientos")
    suspend fun guardarEntrenamiento(
        @Body entrenamiento: Training
    ): Response<Training>
}