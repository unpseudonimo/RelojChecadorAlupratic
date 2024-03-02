package com.example.relojchecadoralupratic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.EmpleadoResponse
import java.util.*

class EmpleadoAdapter(var empleados: List<EmpleadoResponse>) : RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>(),
    Filterable {

    // Lista filtrada de empleados
    var empleadosFiltered: List<EmpleadoResponse> = empleados

    // Inicialización de la lista filtrada
    init {
        empleadosFiltered = empleados
    }

    // ViewHolder para los elementos de la lista
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    }

    // Creación de ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empleado, parent, false)
        return ViewHolder(view)
    }

    // Vinculación de datos a ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = empleadosFiltered[position]
        holder.nameTextView.text = empleado.name
    }

    // Obtención del número de elementos en la lista filtrada
    override fun getItemCount(): Int {
        return empleadosFiltered.size
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
