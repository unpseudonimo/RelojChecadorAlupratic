package com.example.relojchecadoralupratic.ui.fragments.reportes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.adapters.ReporteAdapter
import com.example.relojchecadoralupratic.databinding.FragmentReportesBinding
import com.example.relojchecadoralupratic.viewmodels.ReportesViewModel

class ReportesFragment : Fragment() {

    private var _binding: FragmentReportesBinding? = null
    private val binding get() = _binding!!

    private lateinit var reportesViewModel: ReportesViewModel
    private lateinit var reportesAdapter: ReporteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reportesViewModel = ViewModelProvider(this).get(ReportesViewModel::class.java)

        _binding = FragmentReportesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerViewReportes
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        reportesAdapter = ReporteAdapter()
        recyclerView.adapter = reportesAdapter

        Log.d("ReportesFragment", "Obteniendo archivos ZIP desde el ViewModel...")
        reportesViewModel.obtenerArchivosZip() // Llamamos a la función del ViewModel para obtener los archivos ZIP

        reportesViewModel.archivosZip.observe(viewLifecycleOwner, Observer { archivosZip ->
            reportesAdapter.submitList(archivosZip)
            Log.d("ReportesFragment", "Archivos ZIP recibidos y actualizados en el RecyclerView: $archivosZip")
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
