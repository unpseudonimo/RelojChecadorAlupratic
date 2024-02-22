package com.example.relojchecadoralupratic.network

import com.example.relojchecadoralupratic.models.EmpleadoResponse
import com.example.relojchecadoralupratic.models.LoginResponse
import com.example.relojchecadoralupratic.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    fun login(@Body user: Usuario): Call<LoginResponse>

    @GET("/empleados")
    fun getEmpleados(): Call<List<EmpleadoResponse>>
}
