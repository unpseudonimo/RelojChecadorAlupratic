package com.example.relojchecadoralupratic.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.relojchecadoralupratic.models.EmpleadoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionEmpleadosViewModel : ViewModel() {

    // LiveData para los empleados y los errores
    private val _empleados = MutableLiveData<List<EmpleadoResponse>>(emptyList())
    val empleados: LiveData<List<EmpleadoResponse>> = _empleados

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Método para obtener los empleados
    fun obtenerEmpleados() {
        // Llamar al servicio web para obtener los empleados
        val call = RetrofitClient.webService.getEmpleados()
        call.enqueue(object : Callback<List<EmpleadoResponse>> {
            override fun onResponse(call: Call<List<EmpleadoResponse>>, response: Response<List<EmpleadoResponse>>) {
                if (response.isSuccessful) {
                    // Actualizar el LiveData con los empleados obtenidos
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
