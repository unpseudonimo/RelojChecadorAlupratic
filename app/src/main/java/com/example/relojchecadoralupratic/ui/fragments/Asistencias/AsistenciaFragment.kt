package com.example.relojchecadoralupratic.ui.fragments.Asistencias

import AsistenciaAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AsistenciaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsistenciaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var adapter: AsistenciaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /// Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_asistencia, container, false)

        // Inicializar el RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewAsistencias)

        // Inicializar el adaptador
        adapter = AsistenciaAdapter(emptyList())

        // Establecer el layout manager y el adaptador en el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)

        // Observar los cambios en la lista de asistencias en el ViewModel
        viewModel.asistencias.observe(viewLifecycleOwner, Observer { asistencias ->
            // Actualizar el adaptador con la nueva lista de asistencias
            adapter.actualizarAsistencias(asistencias)
        })

        // Obtener las asistencias del servidor
        viewModel.obtenerAsistencias()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AsistenciaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AsistenciaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}