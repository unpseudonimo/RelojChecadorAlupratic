package com.example.relojchecadoralupratic.ui.fragments.gestionempleados

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.viewmodels.AddEditEmpleadoViewModel

class AddEditEmpleadoFragment : Fragment() {

    companion object {
        fun newInstance() = AddEditEmpleadoFragment()
    }

    private lateinit var viewModel: AddEditEmpleadoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit_empleado, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEditEmpleadoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}