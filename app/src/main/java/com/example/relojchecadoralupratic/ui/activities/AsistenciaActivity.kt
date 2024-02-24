package com.example.relojchecadoralupratic.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel

class AsistenciaActivity : AppCompatActivity() {
    private lateinit var viewModel: AsistenciaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistencia)

        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)
        viewModel.obtenerAsistencias()

        // Observa los cambios en las asistencias y realiza las actualizaciones necesarias en la UI
        viewModel.asistencias.observe(this, { asistencias ->
            // Actualiza la UI con las nuevas asistencias
            // Aquí puedes manejar la lógica para mostrar las asistencias en tu actividad
        })

        // Observa los errores y maneja la lógica correspondiente
        viewModel.error.observe(this, { error ->
            // Maneja el error, muestra un mensaje de error, etc.
        })
    }
}
