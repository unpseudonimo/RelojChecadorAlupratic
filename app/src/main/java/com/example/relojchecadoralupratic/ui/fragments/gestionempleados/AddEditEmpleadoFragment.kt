package com.example.relojchecadoralupratic.ui.fragments.gestionempleados

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.Empleado
import com.example.relojchecadoralupratic.network.RetrofitClient
import com.example.relojchecadoralupratic.viewmodels.AddEditEmpleadoViewModel
import com.example.relojchecadoralupratic.viewmodels.factory.AddEditEmpleadoViewModelFactory

class AddEditEmpleadoFragment : Fragment() {

    private lateinit var viewModel: AddEditEmpleadoViewModel
    private lateinit var editTextName: EditText
    private lateinit var editTextCardNumber: EditText
    private lateinit var spinnerRole: Spinner
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_empleado, container, false)
        editTextName = view.findViewById(R.id.editTextName)
        editTextCardNumber = view.findViewById(R.id.editTextCardNumber)
        spinnerRole = view.findViewById(R.id.spinnerRole)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonSave = view.findViewById(R.id.buttonSave)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEditEmpleadoViewModel::class.java)

        val roles = resources.getStringArray(R.array.roles)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = adapter

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val cardNumber = editTextCardNumber.text.toString()
            val role = spinnerRole.selectedItem.toString()
            val password = editTextPassword.text.toString()

            val empleado = Empleado(name, cardNumber, role, password)
            viewModel.crearEmpleado(empleado) { success ->
                if (success) {
                    // El empleado se creó correctamente
                } else {
                    // Hubo un error al crear el empleado
                }
            }
        }
    }

    companion object {
        fun newInstance() = AddEditEmpleadoFragment()
    }
}
