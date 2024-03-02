package com.example.relojchecadoralupratic.ui.fragments.gestionempleados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.Empleado
import com.example.relojchecadoralupratic.viewmodels.AddEditEmpleadoViewModel

class AddEditEmpleadoFragment : Fragment() {

    private lateinit var viewModel: AddEditEmpleadoViewModel
    private lateinit var editTextName: EditText
    private lateinit var editTextCardNumber: EditText
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_empleado, container, false)
        editTextName = view.findViewById(R.id.editTextName)
        editTextCardNumber = view.findViewById(R.id.editTextCardNumber)
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonSave = view.findViewById(R.id.buttonSave)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEditEmpleadoViewModel::class.java)

        val items = arrayOf("User", "Admin")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val cardNumber = editTextCardNumber.text.toString()
            val role = autoCompleteTextView.text.toString()
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
