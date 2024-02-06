package com.example.relojchecadoralupratic.network

import com.example.relojchecadoralupratic.core.Constantes
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Propiedad webService utilizando la delegación by lazy para inicialización perezosa
    val webService: ApiService by lazy {
        // Crear una instancia de Retrofit con la URL base y un convertidor Gson
        Retrofit.Builder()
            .baseUrl(Constantes.API_URL) // URL base del servicio web
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // Convertidor Gson
            .build()
            .create(ApiService::class.java) // Crear la instancia de WebService a partir de la interfaz
    }
}