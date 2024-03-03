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
import com.example.relojchecadoralupratic.databinding.FragmentInicioBinding
import com.example.relojchecadoralupratic.models.Asistencia
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var adapter: AsistenciaAdapter

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

        // Inicializar el Spinner
        spinner = root.findViewById(R.id.spinnerSedes)

        // Configurar el Spinner con las opciones
        setupSpinner()

        // Configurar el RecyclerView
        setupRecyclerView()

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)

        // Observar los cambios en las asistencias
        adapter = AsistenciaAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@InicioFragment.adapter
        }

        viewModel.asistencias.observe(viewLifecycleOwner, Observer { asistencias ->
            val today = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val todayString = dateFormat.format(today)

            val asistenciasHoy = asistencias.filter { it.fecha_registro == todayString }
            adapter.asistencias = asistenciasHoy.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed()
            adapter.notifyDataSetChanged()
        })

        // Obtener las asistencias
        viewModel.obtenerAsistencias()

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

        return root
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
}
