package com.example.relojchecadoralupratic.network

import com.example.relojchecadoralupratic.models.Asistencia
import com.example.relojchecadoralupratic.models.Empleado
import com.example.relojchecadoralupratic.models.EmpleadoResponse
import com.example.relojchecadoralupratic.models.LoginResponse
import com.example.relojchecadoralupratic.models.Reporte
import com.example.relojchecadoralupratic.models.Usuario
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.File

interface ApiService {
    @POST("/login")
    fun login(@Body user: Usuario): Call<LoginResponse>

    @GET("/obtener_empleado")
    fun getEmpleados(): Call<List<EmpleadoResponse>>

    @POST("/crear_empleado")
    fun crearEmpleado(@Body empleado: Empleado): Call<Boolean>

    @DELETE("/eliminar_empleado/{uid}")
    fun eliminarEmpleado(@Path("uid") uid: Int): Call<Boolean>

    @GET("/asistencias")
    fun getAsistencias(): Call<List<Asistencia>> // Método para obtener las asistencias

    @POST("/generar_reporte")
    fun generarReportePdf(@Body requestBody: RequestBody): Call<ResponseBody>

}