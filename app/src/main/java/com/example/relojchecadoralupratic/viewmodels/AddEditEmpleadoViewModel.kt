package com.example.relojchecadoralupratic.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.relojchecadoralupratic.models.Empleado
import com.example.relojchecadoralupratic.network.ApiService
import com.example.relojchecadoralupratic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditEmpleadoViewModel : ViewModel() {

    private val apiService = RetrofitClient.webService

    fun crearEmpleado(empleado: Empleado, onResult: (Boolean) -> Unit) {
        Log.d("AddEditEmpleadoViewModel", "Realizando solicitud para crear empleado: $empleado")
        apiService.crearEmpleado(empleado).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val exito = response.body()
                if (exito != null && exito) {
                    // El empleado fue creado exitosamente
                    onResult(true)
                } else {
                    // Error al crear el empleado
                    onResult(false)
                }
                Log.d("AddEditEmpleadoViewModel", "Respuesta del servidor: $exito")
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("AddEditEmpleadoViewModel", "Error al realizar la solicitud para crear empleado: ${t.message}")
                onResult(false)
            }
        })
    }
}
