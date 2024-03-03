package com.example.relojchecadoralupratic.ui.fragments.Asistencias

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.AsistenciaDetalleAdapter
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel
class AsistenciaFragment : Fragment() {
    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var adapter: AsistenciaDetalleAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_asistencia, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewAsistencias)

        adapter = AsistenciaDetalleAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AsistenciaFragment.adapter
        }


        viewModel.asistencias.observe(viewLifecycleOwner, Observer { asistencias ->
            adapter.asistencias = asistencias.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed()
            adapter.notifyDataSetChanged()
        })



        viewModel.asistencias.observe(viewLifecycleOwner, {error ->
            Log.e("GestionEmpleadosFragment", "Error: $error")
        })

        viewModel.obtenerAsistencias()
    }

}
