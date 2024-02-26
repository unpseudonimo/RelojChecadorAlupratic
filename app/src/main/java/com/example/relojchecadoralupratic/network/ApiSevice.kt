package com.example.relojchecadoralupratic.network

import com.example.relojchecadoralupratic.models.Asistencia
import com.example.relojchecadoralupratic.models.Empleado
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

    @GET("/obtener_empleado")
    fun getEmpleados(): Call<List<EmpleadoResponse>>

    @POST("/crear_empleado")
    fun crearEmpleado(@Body empleado: Empleado): Call<Boolean>

    @GET("/asistencias")
    fun getAsistencias(): Call<List<Asistencia>> // Método para obtener las asistencias
}
