package com.example.relojchecadoralupratic.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.relojchecadoralupratic.models.Asistencia
import com.example.relojchecadoralupratic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AsistenciaViewModel : ViewModel() {
    private val _asistencias = MutableLiveData<List<Asistencia>>()
    val asistencias: LiveData<List<Asistencia>> = _asistencias

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun obtenerAsistencias() {
        val call = RetrofitClient.webService.getAsistencias()
        call.enqueue(object : Callback<List<Asistencia>> {
            override fun onResponse(call: Call<List<Asistencia>>, response: Response<List<Asistencia>>) {
                if (response.isSuccessful) {
                    _asistencias.value = response.body()
                    Log.d("AsistenciaViewModel", "Respuesta del servidor: ${response.body()}")
                } else {
                    // Manejar errores de respuesta
                    _error.value = "Error al obtener asistencias: ${response.code()}"
                }
            }
            override fun onFailure(call: Call<List<Asistencia>>, t: Throwable) {
                // Manejar errores de comunicación
                _error.value = "Error de comunicación: ${t.message}"
            }
        })
    }
}
