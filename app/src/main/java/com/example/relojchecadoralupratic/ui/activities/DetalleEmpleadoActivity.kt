package com.example.relojchecadoralupratic.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.AsistenciaAdapter
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel

class DetalleEmpleadoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AsistenciaAdapter
    private lateinit var viewModel: AsistenciaViewModel
    private var empleadoId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_empleado)

        // Obtener el ID y el nombre del empleado del intent
        val empleadoId = intent.getIntExtra("empleado_id", 0)
        val empleadoNombre = intent.getStringExtra("empleado_nombre") ?: ""

        // Encontrar los TextView en el layout de la actividad
        val empleadoIdTextView: TextView = findViewById(R.id.textViewEmployeeId)
        val empleadoNombreTextView: TextView = findViewById(R.id.textViewName)

        // Mostrar el ID y el nombre del empleado en los TextView correspondientes
        empleadoIdTextView.text = "${empleadoId.toString()}."
        empleadoNombreTextView.text = "${empleadoNombre}"

        // Obtener ViewModel
        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)

        // Observar la lista de asistencias
        viewModel.asistencias.observe(this, { asistencias ->
            // Filtrar asistencias por el ID del empleado
            val asistenciasFiltradas = asistencias.filter { it.id_empleado.toIntOrNull() == empleadoId }
            // Actualizar el RecyclerView con las asistencias filtradas
            adapter.updateData(asistenciasFiltradas)
        })

        // Inicializar RecyclerView y Adapter
        recyclerView = findViewById(R.id.recyclerViewAsistencias)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AsistenciaAdapter(emptyList())
        recyclerView.adapter = adapter

        // Obtener asistencias del servidor
        viewModel.obtenerAsistencias(this)


    }
}
