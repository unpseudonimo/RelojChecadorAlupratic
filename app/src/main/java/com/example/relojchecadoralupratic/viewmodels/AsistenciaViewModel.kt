package com.example.relojchecadoralupratic.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.relojchecadoralupratic.models.Asistencia
import com.example.relojchecadoralupratic.models.Reporte
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ViewModel
class AsistenciaViewModel : ViewModel() {
    private val _asistencias = MutableLiveData<List<Asistencia>>()
    val asistencias: LiveData<List<Asistencia>> = _asistencias

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _rutaArchivo = MutableStateFlow<String?>(null)
    val rutaArchivo: StateFlow<String?> = _rutaArchivo


    private val _exitoDescarga = MutableLiveData<Boolean>()
    val exitoDescarga: LiveData<Boolean> = _exitoDescarga


    fun guardarRutaArchivo(uri: String) {
        _rutaArchivo.value = uri
    }

    fun obtenerAsistencias(context: Context) {
        val call = RetrofitClient.webService.getAsistencias()
        call.enqueue(object : Callback<List<Asistencia>> {
            override fun onResponse(call: Call<List<Asistencia>>, response: Response<List<Asistencia>>) {
                if (response.isSuccessful) {
                    _asistencias.value = response.body()
                    Log.d("AsistenciaViewModel", "Respuesta del servidor: ${response.body()}")
                } else {
                    // Manejar errores de respuesta
                    val errorMessage = "Error al obtener asistencias: ${response.code()}"
                    _error.value = errorMessage
                    Log.e("AsistenciaViewModel", errorMessage)
                }
            }
            override fun onFailure(call: Call<List<Asistencia>>, t: Throwable) {
                // Manejar errores de comunicación
                val errorMessage = "Error de comunicación: ${t.message}"
                _error.value = errorMessage
                Log.e("AsistenciaViewModel", errorMessage, t)
            }
        })
    }

    fun generarReportePdf(asistenciasFiltradas: List<Asistencia>, uri:Uri, nombreReporte: String, context: Context) {
        viewModelScope.launch {
            try {
                val asistencias = asistenciasFiltradas.map { asistenciaDetalle ->
                    Asistencia(
                        registro = asistenciaDetalle.registro,
                        UID = asistenciaDetalle.UID,
                        tipo_acceso = asistenciaDetalle.tipo_acceso,
                        fecha_registro = asistenciaDetalle.fecha_registro,
                        hora_registro = asistenciaDetalle.hora_registro,
                        id_empleado = asistenciaDetalle.id_empleado,
                        nombre_empleado = asistenciaDetalle.nombre_empleado
                    )
                }
                val reporte = Reporte(asistencias, nombreReporte!!)

                val gson = Gson()
                val json = gson.toJson(reporte)

                val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

                val call = RetrofitClient.webService.generarReportePdf(requestBody)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val inputStream = response.body()?.byteStream()

                            if (inputStream != null) {
                                val outputStream = context.contentResolver.openOutputStream(uri)
                                outputStream?.use { output ->
                                    inputStream.copyTo(output)
                                }
                                guardarRutaArchivo(uri.toString())
                                _exitoDescarga.value = true
                            } else {
                                val errorMessage = "El flujo de entrada del archivo ZIP es nulo"
                                _error.value = errorMessage
                                Log.e("AsistenciaViewModel", errorMessage)
                            }
                        } else {
                            val errorMessage = "Error al generar reporte PDF: ${response.code()}"
                            _error.value = errorMessage
                            Log.e("AsistenciaViewModel", errorMessage)
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        val errorMessage = "Error de comunicación: ${t.message}"
                        _error.value = errorMessage
                        Log.e("AsistenciaViewModel", errorMessage, t)
                    }
                })
            } catch (e: Exception) {
                val errorMessage = "Error al convertir asistencias: ${e.message}"
                _error.value = errorMessage
                Log.e("AsistenciaViewModel", errorMessage, e)
            }
        }
    }

}
