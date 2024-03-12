package com.example.relojchecadoralupratic.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.relojchecadoralupratic.models.Empleado
import com.example.relojchecadoralupratic.models.RespuestaCreacionEmpleado
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditEmpleadoViewModel : ViewModel() {

    private val apiService = RetrofitClient.webService

    fun crearEmpleado(empleado: Empleado, onResult: (Boolean) -> Unit) {
        Log.d("AddEditEmpleadoViewModel", "Realizando solicitud para crear empleado: $empleado")
        apiService.crearEmpleado(empleado).enqueue(object : Callback<RespuestaCreacionEmpleado> {
            override fun onResponse(call: Call<RespuestaCreacionEmpleado>, response: Response<RespuestaCreacionEmpleado>) {
                val respuesta = response.body()
                if (response.isSuccessful && respuesta != null && respuesta.mensaje == "Operación exitosa:") {
                    // El empleado se creó correctamente
                    onResult(true)
                    Log.d("AddEditEmpleadoViewModel", "Empleado creado correctamente")
                } else {
                    // Hubo un error al crear el empleado
                    onResult(false)
                    val mensajeError = respuesta?.mensaje ?: response.errorBody()?.string()
                    Log.e("AddEditEmpleadoViewModel", "Error al crear el empleado: $mensajeError")
                }
            }

            override fun onFailure(call: Call<RespuestaCreacionEmpleado>, t: Throwable) {
                // Hubo un error al realizar la solicitud
                onResult(false)
                Log.e("AddEditEmpleadoViewModel", "Error al realizar la solicitud para crear empleado: ${t.message}")
            }
        })
    }
}
