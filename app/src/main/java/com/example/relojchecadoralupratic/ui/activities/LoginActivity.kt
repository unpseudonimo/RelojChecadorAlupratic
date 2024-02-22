package com.example.relojchecadoralupratic.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.relojchecadoralupratic.MainActivity
import com.example.relojchecadoralupratic.databinding.ActivityLoginBinding
import com.example.relojchecadoralupratic.models.Usuario
import com.example.relojchecadoralupratic.viewmodels.LoginViewModel
import com.example.relojchecadoralupratic.viewmodels.factory.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del ViewModel
        viewModel = ViewModelProvider(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val user = Usuario(binding.usernameEditText.text.toString(), binding.passwordEditText.text.toString())
            Log.d("LoginActivity", "Iniciando sesión para el usuario: $user")
            viewModel.login(user) { response ->
                if (response != null && response.token != null) {
                    // El login fue exitoso, redirigir a la pantalla principal
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Finalizar la actividad actual para evitar que el usuario regrese presionando el botón Atrás
                    // Mostrar Toast de ingreso exitoso
                    Toast.makeText(this, "Ingreso al sistema correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    // El login no fue exitoso
                    Log.e("LoginActivity", "Error al iniciar sesión")
                    // Mostrar Toast de error al iniciar sesión
                    Toast.makeText(this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
