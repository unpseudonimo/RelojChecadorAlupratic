package com.example.relojchecadoralupratic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.EmpleadoResponse

class EmpleadoAdapter(var empleados: List<EmpleadoResponse>) : RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uidTextView: TextView = itemView.findViewById(R.id.uidTextView)
        val useridTextView: TextView = itemView.findViewById(R.id.useridTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val passwordTextView: TextView = itemView.findViewById(R.id.passwordTextView)
        val roleTextView: TextView = itemView.findViewById(R.id.roleTextView)
        val cardnoTextView: TextView = itemView.findViewById(R.id.cardnoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empleado, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = empleados[position]
        holder.uidTextView.text = empleado.uid.toString()
        holder.useridTextView.text = empleado.userid
        holder.nameTextView.text = empleado.name
        holder.roleTextView.text = empleado.role.toString()
        holder.cardnoTextView.text = empleado.cardno?.toString() ?: "N/A"
    }

    override fun getItemCount(): Int {
        return empleados.size
    }

}
