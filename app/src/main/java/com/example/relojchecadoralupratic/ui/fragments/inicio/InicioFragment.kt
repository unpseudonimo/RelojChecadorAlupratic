package com.example.relojchecadoralupratic.ui.fragments.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.AsistenciaAdapter
import com.example.relojchecadoralupratic.databinding.FragmentInicioBinding
import com.example.relojchecadoralupratic.models.Asistencia

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Referencias a las vistas
        spinner = binding.spinnerSedes
        recyclerView = binding.recyclerView1

        // Configurar el Spinner con las opciones
        setupSpinner()

        // Configurar el RecyclerView
        setupRecyclerView()

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
        // Configurar el adaptador para el RecyclerView
        val adapter = AsistenciaAdapter(getSampleAsistencias())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    // Método para obtener datos de muestra para el RecyclerView
    private fun getSampleAsistencias(): List<Asistencia> {
        val asistencias = mutableListOf<Asistencia>()
        for (i in 1..20) {
            asistencias.add(
                Asistencia(
                    salida = "Salida $i",
                    registro = if (i % 2 == 0) "Check-in" else "Check-out",
                    UID = i,
                    tipo_acceso = "Tipo acceso $i",
                    fecha_registro = "Fecha: $i",
                    hora_registro = "Hora: ",
                    id_empleado = "ID $i",
                    nombre_empleado = "Nombre $i"
                )
            )
        }
        return asistencias
    }
}