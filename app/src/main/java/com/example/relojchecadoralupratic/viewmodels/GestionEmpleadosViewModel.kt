package com.example.relojchecadoralupratic.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.relojchecadoralupratic.models.Empleado
import com.example.relojchecadoralupratic.models.EmpleadoResponse
import com.example.relojchecadoralupratic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionEmpleadosViewModel : ViewModel() {

    private val _empleados = MutableLiveData<List<EmpleadoResponse>>()
    val empleados: LiveData<List<EmpleadoResponse>> = _empleados

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun obtenerEmpleados() {
        val call = RetrofitClient.webService.getEmpleados()
        call.enqueue(object : Callback<List<EmpleadoResponse>> {
            override fun onResponse(call: Call<List<EmpleadoResponse>>, response: Response<List<EmpleadoResponse>>) {
                if (response.isSuccessful) {
                    _empleados.value = response.body()
                    Log.d("GestionEmpleadosViewModel", "Respuesta del servidor: ${response.body()}")
                } else {
                    // Manejar errores de respuesta
                    _error.value = "Error al obtener empleados: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<EmpleadoResponse>>, t: Throwable) {
                // Manejar errores de comunicación
                _error.value = "Error de comunicación: ${t.message}"
            }
        })
    }

}
