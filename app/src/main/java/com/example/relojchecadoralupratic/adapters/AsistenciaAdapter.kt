import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.models.Asistencia
import java.text.SimpleDateFormat
import java.util.Locale

class AsistenciaAdapter(var asistencias: List<Asistencia>) : RecyclerView.Adapter<AsistenciaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uidTextView: TextView = itemView.findViewById(R.id.uidTextView)
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val accesoTextView: TextView = itemView.findViewById(R.id.accesoTextView)
        val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        val horaTextView: TextView = itemView.findViewById(R.id.horaTextView)
        val registroTextView: TextView = itemView.findViewById(R.id.registroTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asistencia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asistencia = asistencias[position]
        holder.uidTextView.text = "UID: ${asistencia.UID}"
        holder.idTextView.text = "ID: ${asistencia.id_empleado}"
        holder.nameTextView.text = "Name: ${asistencia.nombre_empleado}"
        holder.accesoTextView.text = "Acceso: ${asistencia.tipo_acceso}"
        holder.fechaTextView.text = "Fecha: ${asistencia.fecha_registro}"
        holder.horaTextView.text = "Hora: ${asistencia.hora_registro}"

        // Verificar si la entrada es Check-in o Check-out y mostrarla en el TextView correspondiente
        val registro = if (asistencia.registro == "Check-in") "Entrada" else "Salida"
        holder.registroTextView.text = "Registro: $registro"
    }

    override fun getItemCount(): Int {
        return asistencias.size
    }
    // Método para actualizar la lista de asistencias
    fun actualizarAsistencias(nuevaLista: List<Asistencia>) {
        asistencias = nuevaLista
        notifyDataSetChanged()
    }
}
