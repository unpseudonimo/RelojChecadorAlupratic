package com.example.relojchecadoralupratic.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R

class ReporteAdapter : ListAdapter<String, ReporteAdapter.ReporteViewHolder>(ReportesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_reporte, parent, false)
        return ReporteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReporteViewHolder, position: Int) {
        val nombreReporte = getItem(position)
        val showDivider = position < itemCount - 1 // Mostrar el separador para todos los elementos excepto el último
        holder.bind(nombreReporte, showDivider)
    }


    class ReporteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textNombreReporte)
        private val divider: View = itemView.findViewById(R.id.divider)

        fun bind(nombreReporte: String, showDivider: Boolean) {
            textView.text = nombreReporte
            divider.visibility = if (showDivider) View.VISIBLE else View.GONE
        }
    }


    class ReportesDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
