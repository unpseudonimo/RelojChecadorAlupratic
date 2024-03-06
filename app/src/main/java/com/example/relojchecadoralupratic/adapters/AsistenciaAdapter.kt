package com.example.relojchecadoralupratic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.Asistencia

class AsistenciaAdapter(var asistencias: List<Asistencia>) : RecyclerView.Adapter<AsistenciaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val accesoTextView: TextView = itemView.findViewById(R.id.accesoTextView)
        val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        val horaTextView: TextView = itemView.findViewById(R.id.horaTextView)
        val registroTextView: TextView = itemView.findViewById(R.id.registroTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asistencia_detalle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asistencia = asistencias[position]
        holder.nameTextView.text =  asistencia.nombre_empleado
        holder.fechaTextView.text = "Fecha: ${asistencia.fecha_registro}"
        holder.horaTextView.text = "Hora: ${asistencia.hora_registro}"

        // Asignar el color correspondiente a cada opción de registro
        val color = when (asistencia.registro) {
            "Check-in" -> R.color.checkInColor
            "Check-out" -> R.color.checkOutColor
            "Overtime-in" -> R.color.overtimeInColor
            "Overtime-out" -> R.color.overtimeOutColor
            else -> androidx.appcompat.R.color.abc_tint_default
        }

        // Cambiar los valores de registro
        val registro = when (asistencia.registro) {
            "Check-in" -> "Entrada"
            "Check-out" -> "Salida"
            "Overtime-in" -> "Hora extra entrada"
            "Overtime-out" -> "Hora extra salida"
            else -> "Desconocido"
        }

        val acceso = when (asistencia.tipo_acceso){
            "Fingerprint" -> "Huella dactilar"
            else -> "contraseña"
        }

        holder.registroTextView.text = registro
        holder.registroTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, color))
        holder.accesoTextView.text = "Acceso: ${acceso}"
    }

    override fun getItemCount(): Int {
        return asistencias.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    // Método para actualizar los datos del adaptador
    fun updateData(newAsistencias: List<Asistencia>) {
        asistencias = newAsistencias
        notifyDataSetChanged()
    }
}

