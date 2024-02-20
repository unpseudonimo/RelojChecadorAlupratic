package com.example.relojchecadoralupratic.ui.fragments.gestionempleados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.relojchecadoralupratic.databinding.FragmentGestionempleadosBinding
import com.example.relojchecadoralupratic.viewmodels.GestionEmpleadosViewModel

class GestionEmpleadosFragment : Fragment() {

    private var _binding: FragmentGestionempleadosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gestionEmpleadosViewModel =
            ViewModelProvider(this).get(GestionEmpleadosViewModel::class.java)

        _binding = FragmentGestionempleadosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        gestionEmpleadosViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}