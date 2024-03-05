package com.example.relojchecadoralupratic.ui.fragments.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.AsistenciaAdapter
import com.example.relojchecadoralupratic.adapters.AsistenciaDpAdapter
import com.example.relojchecadoralupratic.databinding.FragmentInicioBinding
import com.example.relojchecadoralupratic.models.Asistencia
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var adapter: AsistenciaAdapter
    private lateinit var adapter2: AsistenciaDpAdapter
    private lateinit var btnOpenDataPicker: MaterialButton

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Referencias a las vistas
        recyclerView = binding.recyclerView1
        recyclerView2 =  binding.recyclerView2
        spinner = root.findViewById(R.id.spinnerSedes)
        btnOpenDataPicker = root.findViewById(R.id.btnOpenDatePicker)

        // Configurar el Spinner con las opciones
        setupSpinner()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        setupRecyclerView()

        //funcion onclicklistenmer
        btnOpenDataPicker.setOnClickListener {
            // Mostrar el DatePicker
            showDatePicker()
        }

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)

        // Observar los cambios en las asistencias
        adapter = AsistenciaAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@InicioFragment.adapter
        }

        // Configurar el RecyclerView2 con un adaptador vacío
        adapter2 = AsistenciaDpAdapter(emptyList())
        recyclerView2.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@InicioFragment.adapter2
        }

        viewModel.asistencias.observe(viewLifecycleOwner, Observer { asistencias ->
            val today = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val todayString = dateFormat.format(today)

            val asistenciasHoy = asistencias.filter { it.fecha_registro == todayString }
            adapter.asistencias = asistenciasHoy.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed()
            adapter.notifyDataSetChanged()
        })

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                val buttons = group.children
                buttons.forEach { button ->
                    if (button.id != checkedId) {
                        val otherButton = group.findViewById<MaterialButton>(button.id)
                        otherButton.isChecked = false
                        otherButton.isEnabled = true
                    }
                }

                // Desmarcar manualmente el botón seleccionado
                val checkedButton = group.findViewById<MaterialButton>(checkedId)
                checkedButton.isChecked = false

                // Filtrar las asistencias según el botón seleccionado
                when (checkedId) {
                    R.id.hoyfilter -> {
                        filterAsistencias("hoy")
                        updateTitle("hoy")
                    }
                    R.id.ayerfilter -> {
                        filterAsistencias("ayer")
                        updateTitle("ayer")
                    }
                    R.id.quincenalfilter -> {
                        filterAsistencias("quincenal")
                        updateTitle("quincenal")
                    }
                }
            }
        }

        // Obtener las asistencias
        viewModel.obtenerAsistencias(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Configurar el Spinner con las opciones
    private fun setupSpinner() {
        // Obtener las opciones del Spinner desde el array en strings.xml
        val opcionesSedes = resources.getStringArray(R.array.sedes_array)

        // Crear un adaptador para el Spinner
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesSedes)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner
    }

    // Configurar el RecyclerView
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView2.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateTitle(filter: String) {
        val title = when (filter) {
            "hoy" -> "Asistencias de Hoy"
            "ayer" -> "Asistencias de Ayer"
            "quincenal" -> "Asistencias de la Última Quincena"
            else -> "Asistencias"
        }
        binding.textViewTitulo.text = title
    }

    // Método para filtrar las asistencias según el botón seleccionado
    private fun filterAsistencias(filter: String) {
        val asistencias = viewModel.asistencias.value ?: return

        val filteredAsistencias = when (filter) {
            "hoy" -> filterAsistenciasHoy(asistencias)
            "ayer" -> filterAsistenciasAyer(asistencias)
            "quincenal" -> filterAsistenciasQuincenal(asistencias)
            else -> asistencias
        }

        adapter.asistencias = filteredAsistencias.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed()
        adapter.notifyDataSetChanged()
    }

    // Método para filtrar las asistencias de hoy
    private fun filterAsistenciasHoy(asistencias: List<Asistencia>): List<Asistencia> {
        val today = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val todayString = dateFormat.format(today)

        return asistencias.filter { it.fecha_registro == todayString }
    }

    // Método para filtrar las asistencias de ayer
    private fun filterAsistenciasAyer(asistencias: List<Asistencia>): List<Asistencia> {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val yesterdayString = dateFormat.format(yesterday.time)

        return asistencias.filter { it.fecha_registro == yesterdayString }
    }
    // Método para filtrar las asistencias de la última quincena
    private fun filterAsistenciasQuincenal(asistencias: List<Asistencia>): List<Asistencia> {
        val today = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val todayString = dateFormat.format(today)

        val quincena = Calendar.getInstance()
        quincena.add(Calendar.DATE, -15)
        val quincenaString = dateFormat.format(quincena.time)

        return asistencias.filter { it.fecha_registro in quincenaString..todayString }
    }

    private fun showDatePicker() {
        // Limpiar las variables
        adapter2.asistencias = emptyList()
        adapter2.notifyDataSetChanged()
        binding.textViewSelectedDate.text = ""

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
            binding.textViewSelectedDate.text = "$startDateString - $endDateString"

            // Filtrar las asistencias por el rango de fechas seleccionado
            filterAsistenciasPorRangoFechas(startDate, endDate)
        }

        datePicker.show(requireFragmentManager(), "datePicker")
    }


    private fun filterAsistenciasPorRangoFechas(fechaInicio: Date, fechaFin: Date) {
        val asistencias = viewModel.asistencias.value ?: return

        val filteredAsistencias = asistencias.filter { asistencia ->
            val asistenciaDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(asistencia.fecha_registro)
            val asistenciaTime = asistenciaDate.time
            val fechaInicioTime = fechaInicio.time
            val fechaFinTime = fechaFin.time + 86400000 // Agregar 24 horas para incluir todas las asistencias del día de la fecha de fin

            asistenciaTime in fechaInicioTime..fechaFinTime || asistenciaTime == fechaInicioTime || asistenciaTime == fechaFinTime
        }

        adapter2.asistencias = filteredAsistencias.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed()
        adapter2.notifyDataSetChanged()
    }




}
