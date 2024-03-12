package com.example.relojchecadoralupratic.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.AsistenciaAdapter
import com.example.relojchecadoralupratic.adapters.AsistenciaDpAdapter
import com.example.relojchecadoralupratic.models.Asistencia
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DetalleEmpleadoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter: AsistenciaAdapter
    private lateinit var adapter2: AsistenciaDpAdapter

    private lateinit var viewModel: AsistenciaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_empleado)

        // Obtener el ID y el nombre del empleado del intent
        val empleadoId = intent.getIntExtra("empleado_id", 0)
        val empleadoNombre = intent.getStringExtra("empleado_nombre") ?: ""

        // Encontrar los TextView en el layout de la actividad
        val empleadoIdTextView: TextView = findViewById(R.id.textViewEmployeeId)
        val empleadoNombreTextView: TextView = findViewById(R.id.textViewName)
        val btnOpenDataPicker: MaterialButton = findViewById(R.id.btnOpenDatePicker)
        val tvDiasTrabajados: TextView = findViewById(R.id.tvHorasTrabajadas)

        // Mostrar el ID y el nombre del empleado en los TextView correspondientes
        empleadoIdTextView.text = "Id: ${empleadoId.toString()}"
        empleadoNombreTextView.text = "Nombre: ${empleadoNombre}"

        //funcion onclicklistenmer
        btnOpenDataPicker.setOnClickListener {
            // Mostrar el DatePicker
            showDatePicker(empleadoId, empleadoNombre)
        }

        // Obtener ViewModel
        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)

        // Inicializar RecyclerView y Adapter
        recyclerView = findViewById(R.id.recyclerViewAsistencias)
        recyclerView2 = findViewById(R.id.recyclerViewAsistenciasRango)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView2.layoutManager = LinearLayoutManager(this)

        adapter = AsistenciaAdapter(emptyList())
        adapter2 = AsistenciaDpAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView2.adapter = adapter2

        // Observar la lista de asistencias
        viewModel.asistencias.observe(this, { asistencias ->
            // Filtrar asistencias por el ID del empleado
            val asistenciasFiltradas = asistencias.filter { it.id_empleado.toIntOrNull() == empleadoId }.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed()
            val asistenciasHoy = filterAsistenciasHoy(asistenciasFiltradas)
            val diasTrabajados = CalcularDiasTrabajados(asistenciasFiltradas)

            // Actualizar el RecyclerView con las asistencias filtradas
            tvDiasTrabajados.text = "Días laborados: $diasTrabajados"
            adapter.updateData(asistenciasHoy)
        })

        // Obtener asistencias del servidor
        viewModel.obtenerAsistencias(this)
    }

    private fun filterAsistenciasHoy(asistencias: List<Asistencia>): List<Asistencia> {
        val today = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val todayString = dateFormat.format(today)

        return asistencias.filter { it.fecha_registro == todayString }
    }

    private fun showDatePicker(id: Int, nombre: String) {
        // Limpiar las variables
        adapter2.asistencias = emptyList()
        adapter2.notifyDataSetChanged()
        val fechaseleccionada: TextView = findViewById(R.id.textViewSelectedDate)
        fechaseleccionada.text = ""

        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Selecciona un rango de fechas")
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            // Handle date selection
            val startDate = Date(selection.first)
            val endDate = Date(selection.second)
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Establecer la zona horaria en UTC
            val startDateString = dateFormat.format(startDate)
            val endDateString = dateFormat.format(endDate)

            // Update UI or perform any other action with the selected dates
            // For example, you can update a TextView with the selected dates
            fechaseleccionada.text = "$startDateString - $endDateString"

            // Filtrar las asistencias por el rango de fechas seleccionado
            filterAsistenciasPorRangoFechas(startDate, endDate, id, nombre)
        }

        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun filterAsistenciasPorRangoFechas(fechaInicio: Date, fechaFin: Date, id: Int, nombre: String) {
        val asistencias = viewModel.asistencias.value ?: return

        val filteredAsistencias = asistencias.filter { asistencia ->
            val asistenciaDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(asistencia.fecha_registro)
            val asistenciaTime = asistenciaDate.time
            val fechaInicioTime = fechaInicio.time
            val fechaFinTime = fechaFin.time + 86400000 // Agregar 24 horas para incluir todas las asistencias del día de la fecha de fin

            asistenciaTime in fechaInicioTime..fechaFinTime || asistenciaTime == fechaInicioTime || asistenciaTime == fechaFinTime
        }

        adapter2.asistencias = filteredAsistencias.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed().filter { it.id_empleado.toIntOrNull() == id }
        adapter2.notifyDataSetChanged()
        adapter2.updateData(adapter2.asistencias)
    }

    private fun CalcularDiasTrabajados(asistencias: List<Asistencia>): Int {
        val diasTrabajados = mutableSetOf<String>()

        for (asistencia in asistencias) {
            // Verificar si se ha registrado un "Check-in" para este día
            if (asistencia.registro == "Check-in") {
                diasTrabajados.add(asistencia.fecha_registro)
            }
        }

        Log.d("DetalleEmpleadoActivity", "Días trabajados: ${diasTrabajados.size}")
        return diasTrabajados.size
    }

}
