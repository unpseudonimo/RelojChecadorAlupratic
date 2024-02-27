package com.example.relojchecadoralupratic.ui.fragments.gestionempleados

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
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.EmpleadoAdapter
import com.example.relojchecadoralupratic.viewmodels.GestionEmpleadosViewModel

class GestionEmpleadosFragment : Fragment() {

    private lateinit var viewModel: GestionEmpleadosViewModel
    private lateinit var adapter: EmpleadoAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GestionEmpleadosViewModel::class.java)
        return inflater.inflate(R.layout.fragment_gestion_empleados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)

        adapter = EmpleadoAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@GestionEmpleadosFragment.adapter
        }

        viewModel.empleados.observe(viewLifecycleOwner, Observer { empleados ->
            adapter.empleados = empleados
            adapter.notifyDataSetChanged()

        })


        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            // Mostrar el mensaje de error en un log
            Log.e("GestionEmpleadosFragment", "Error: $error")
        })


        viewModel.obtenerEmpleados()
    }
}
