package com.example.relojchecadoralupratic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.relojchecadoralupratic.models.EmpleadoResponse
import com.example.relojchecadoralupratic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionEmpleadosViewModel : ViewModel() {

    private val _empleados = MutableLiveData<List<EmpleadoResponse>>()
    val empleados: LiveData<List<EmpleadoResponse>> = _empleados

    fun obtenerEmpleados() {
        val call = RetrofitClient.webService.getEmpleados()
        call.enqueue(object : Callback<List<EmpleadoResponse>> {
            override fun onResponse(call: Call<List<EmpleadoResponse>>, response: Response<List<EmpleadoResponse>>) {
                if (response.isSuccessful) {
                    _empleados.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<EmpleadoResponse>>, t: Throwable) {
                // Manejar el error
            }
        })
    }
}
