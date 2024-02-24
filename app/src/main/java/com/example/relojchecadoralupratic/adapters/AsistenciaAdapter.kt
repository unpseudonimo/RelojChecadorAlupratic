package com.example.relojchecadoralupratic.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.Asistencia

class AsistenciaAdapter(var asistencias: List<Asistencia>) : RecyclerView.Adapter<AsistenciaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uidTextView: TextView = itemView.findViewById(R.id.uidTextView)
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val accesoTextView: TextView = itemView.findViewById(R.id.accesoTextView)
        val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        val horaTextView: TextView = itemView.findViewById(R.id.horaTextView)
        val entradaTextView: TextView = itemView.findViewById(R.id.entradaTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asistencia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asistencia = asistencias[position]
        holder.uidTextView.text = asistencia.UID.toString()
        holder.idTextView.text = asistencia.id
        holder.nameTextView.text = asistencia.name
        holder.accesoTextView.text = asistencia.acceso
        holder.fechaTextView.text = asistencia.fecha
        holder.horaTextView.text = asistencia.hora
        holder.entradaTextView.text = asistencia.entrada
    }

    override fun getItemCount(): Int {
        return asistencias.size
    }
}
