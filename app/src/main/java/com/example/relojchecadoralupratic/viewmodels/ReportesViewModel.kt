package com.example.relojchecadoralupratic.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportesViewModel : ViewModel() {

    private val _archivosZip = MutableLiveData<List<String>>()
    val archivosZip: LiveData<List<String>> = _archivosZip

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun obtenerArchivosZip() {
        val call = RetrofitClient.webService.obtenerArchivosZip()

        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val archivosZip = response.body()
                    if (archivosZip != null && archivosZip.isNotEmpty()) {
                        val nombresArchivos = archivosZip.map { archivo ->
                            archivo.substringAfterLast('\\').substringBeforeLast('.') // Obtener solo el nombre sin la extensión y sin el prefijo "docs\"
                        }
                        _archivosZip.value = nombresArchivos
                        Log.d("ReportesViewModel", "Archivos ZIP obtenidos correctamente: $nombresArchivos")
                    } else {
                        Log.e("ReportesViewModel", "La lista de archivos ZIP es nula o está vacía")
                    }
                } else {
                    val errorMessage = "Error al obtener los archivos ZIP: ${response.message()}"
                    _error.value = errorMessage
                    _archivosZip.value = emptyList() // Establecer a una lista vacía en caso de error
                    Log.e("ReportesViewModel", errorMessage)
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                val errorMessage = "Fallo al realizar la solicitud: ${t.message}"
                _error.value = errorMessage
                Log.e("ReportesViewModel", errorMessage, t)
            }
        })
    }
}
