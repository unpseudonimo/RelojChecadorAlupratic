import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.viewmodels.AddEditEmpleadoViewModel

class AddEditEmpleadoFragment : Fragment() {

    companion object {
        fun newInstance() = AddEditEmpleadoFragment()
    }

    private lateinit var viewModel: AddEditEmpleadoViewModel
    private lateinit var editTextName: EditText
    private lateinit var editTextCardNumber: EditText
    private lateinit var editTextRole: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_empleado, container, false)
        editTextName = view.findViewById(R.id.editTextName)
        editTextCardNumber = view.findViewById(R.id.editTextCardNumber)
        editTextRole = view.findViewById(R.id.editTextRole)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonSave = view.findViewById(R.id.buttonSave)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEditEmpleadoViewModel::class.java)
        // TODO: Use the ViewModel

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val cardNumber = editTextCardNumber.text.toString()
            val role = editTextRole.text.toString()
            val password = editTextPassword.text.toString()

            // Aquí puedes guardar la información del formulario utilizando el ViewModel
        }
    }

}
