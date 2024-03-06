package com.example.relojchecadoralupratic.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.EmpleadoResponse
import com.example.relojchecadoralupratic.ui.activities.DetalleEmpleadoActivity
import java.util.*

class EmpleadoAdapter(var empleados: List<EmpleadoResponse>, private var navController: NavController) : RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>(),
    Filterable {

    // Lista filtrada de empleados
    var empleadosFiltered: List<EmpleadoResponse> = empleados

    // Variable para indicar si el filtro está activo
    private var isFilterActive: Boolean = false

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    // ViewHolder para los elementos de la lista
    // ViewHolder para los elementos de la lista
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val idEmpleado: TextView = itemView.findViewById(R.id.idEmpleado)

        init {
            // Agregar OnClickListener al itemView
            itemView.setOnClickListener {
                // Obtener la lista de empleados a usar
                val listaEmpleados = if (empleadosFiltered.isNotEmpty()) {
                    empleadosFiltered
                } else {
                    empleados
                }

                // Verificar si la lista de empleados no está vacía
                if (listaEmpleados.isNotEmpty()) {
                    // Obtener el empleado correspondiente al ViewHolder
                    val empleado = listaEmpleados[adapterPosition]
                    Log.d("EmpleadoAdapter", "Empleado seleccionado: $empleado")
                    // Navegar a la actividad DetalleEmpleadoActivity
                    val intent = Intent(itemView.context, DetalleEmpleadoActivity::class.java)
                    intent.putExtra("empleado_id", empleado.id.toInt()) // Convertir a Int
                    intent.putExtra("empleado_nombre", empleado.name) // Pasar el nombre del empleado
                    itemView.context.startActivity(intent)
                }
            }
        }

        // Vincular datos a la vista
        fun bind(empleado: EmpleadoResponse) {
            nameTextView.text = empleado.name
            idEmpleado.text = "${empleado.id}."
        }
    }

    // Creación de ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empleado, parent, false)
        return ViewHolder(view)
    }

    // Vinculación de datos a ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = if (isFilterActive && empleadosFiltered.isNotEmpty()) {
            empleadosFiltered[position]
        } else {
            empleados[position]
        }
        Log.d("EmpleadoAdapter", "Empleado mostrado en la posición $position: $empleado")
        holder.bind(empleado)
    }

    // Obtención del número de elementos en la lista filtrada
    override fun getItemCount(): Int {
        return if (isFilterActive && empleadosFiltered.isNotEmpty()) {
            empleadosFiltered.size
        } else {
            empleados.size
        }
    }

    // Añadir decoración de separadores a la lista
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    // Obtener el filtro para la lista
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<EmpleadoResponse>()
                val filterPattern = constraint?.toString()?.toLowerCase(Locale.getDefault())?.trim() ?: ""
                for (item in empleados) {
                    if (item.name.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                isFilterActive = constraint?.isNotEmpty() ?: false
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    if (constraint.isNullOrEmpty()) {
                        // Si el constraint es nulo o vacío, mostrar todos los empleados sin filtrar
                        empleadosFiltered = empleados
                    } else {
                        // Si hay un constraint, aplicar el filtro
                        empleadosFiltered = it.values as List<EmpleadoResponse>
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }
}
