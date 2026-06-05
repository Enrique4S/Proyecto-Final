package com.example.theftiness_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    /*
     * EMULADOR:
     * http://10.0.2.2:3000/
     *
     * CELULAR FÍSICO:
     * http://192.168.X.X:3000/
     * (cambiar por la IP de tu computadora)
     */
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}