package com.example.relojchecadoralupratic.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.relojchecadoralupratic.network.RetrofitClient
import com.example.relojchecadoralupratic.models.LoginResponse
import com.example.relojchecadoralupratic.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {
    private val apiService = RetrofitClient.webService
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE)

    fun login(user: Usuario, onResult: (LoginResponse?) -> Unit) {
        Log.d("LoginViewModel", "Realizando solicitud de inicio de sesión para el usuario: $user")
        apiService.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                if (loginResponse != null && loginResponse.token != null) {
                    // El login fue exitoso, guardar el estado de inicio de sesión en SharedPreferences
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                    sharedPreferences.edit().putString("username", user.nombre_usuario).apply() // Guardar el nombre de usuario en SharedPreferences
                }
                onResult(loginResponse)
                Log.d("LoginViewModel", "Respuesta del servidor: $loginResponse")
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginViewModel", "Error al realizar la solicitud de inicio de sesión: ${t.message}")
                onResult(null)
            }
        })
    }
}
