package com.example.relojchecadoralupratic.ui.fragments.gestionempleados

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.EmpleadoAdapter
import com.example.relojchecadoralupratic.viewmodels.GestionEmpleadosViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class GestionEmpleadosFragment : Fragment() {

    private lateinit var viewModel: GestionEmpleadosViewModel
    private lateinit var adapter: EmpleadoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var btnRegistrar: ExtendedFloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_gestion_empleados, container, false)

        // Inicializar el RecyclerView y el SearchView
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        btnRegistrar = view.findViewById(R.id.fabRegistrarPersonal)

        // Inicializar el Adapter
        adapter = EmpleadoAdapter(emptyList(), findNavController())

        // Configurar el RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@GestionEmpleadosFragment.adapter
        }

        // Configurar el SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegistrar.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.nav_addempleados)
        }

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this).get(GestionEmpleadosViewModel::class.java)

        // Observar los LiveData del ViewModel
        viewModel.empleados.observe(viewLifecycleOwner, Observer { empleados ->
            if (empleados.isEmpty()) {
                // Mostrar un mensaje indicando que no hay empleados disponibles
                // (por ejemplo, un Snackbar o un Toast)
            } else {
                adapter.empleados = empleados
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            // Mostrar un mensaje de error en la interfaz de usuario
            // (por ejemplo, un Snackbar o un Toast)
            Log.e("GestionEmpleadosFragment", "Error: $error")
        })

        // Obtener los empleados
        viewModel.obtenerEmpleados()

        // Dentro del método onViewCreated después de la obtención de empleados
        adapter.setNavController(findNavController())



    }
}
