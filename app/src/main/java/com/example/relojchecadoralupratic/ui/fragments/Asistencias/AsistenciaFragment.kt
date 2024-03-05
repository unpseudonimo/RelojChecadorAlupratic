
package com.example.relojchecadoralupratic.ui.fragments.Asistencias

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relojchecadoralupratic.R
import com.example.relojchecadoralupratic.adapters.AsistenciaDetalleAdapter
import com.example.relojchecadoralupratic.viewmodels.AsistenciaViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.File

class AsistenciaFragment : Fragment() {
    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var adapter: AsistenciaDetalleAdapter
    private lateinit var recyclerViewAsistencias: RecyclerView
    private lateinit var fabDescargarReporte: ExtendedFloatingActionButton

    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1001
    private val REQUEST_SAVE_FILE = 1002

    // Variable para almacenar el nombre del reporte
    private var nombreReporte: String = null.toString()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AsistenciaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_asistencia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        recyclerViewAsistencias = view.findViewById(R.id.recyclerViewAsistencias)

        recyclerViewAsistencias.layoutManager = LinearLayoutManager(requireContext())
        adapter = AsistenciaDetalleAdapter(emptyList())
        recyclerViewAsistencias.adapter = adapter

        fabDescargarReporte = view.findViewById(R.id.fabDescargarReporte)

        fabDescargarReporte.setOnClickListener {
            showReportDialog()
        }

        viewModel.asistencias.observe(viewLifecycleOwner, Observer { asistencias ->
            adapter.asistencias = asistencias.sortedWith(compareBy({ it.fecha_registro }, { it.hora_registro })).reversed()
            adapter.notifyDataSetChanged()
        })

        viewModel.exitoDescarga.observe(viewLifecycleOwner, Observer { exito ->
            if (exito) {
                viewModel.rutaArchivo.value?.let { ruta ->
                    // Mostrar Snackbar indicando que el archivo se guardó con éxito
                    view?.let { rootView ->
                        Snackbar.make(rootView, "El archivo se guardó correctamente", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        })


        viewModel.obtenerAsistencias(requireContext())
    }

    private fun showReportDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_reporte_nombre, null)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Generar reporte")
            .setMessage("Escribe el nombre del reporte:")
            .setView(dialogView)
            .setPositiveButton("Aceptar") { dialog, which ->
                val nombreReporte = dialogView.findViewById<EditText>(R.id.editTextNombreReporte).text.toString()
                requestStoragePermission(nombreReporte)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun requestStoragePermission(nombreReporte: String) {
        this.nombreReporte = nombreReporte // Asignar el nombre del reporte a la variable de clase
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_WRITE_EXTERNAL_STORAGE
            )
        } else {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/zip"
                putExtra(Intent.EXTRA_TITLE, nombreReporte + ".zip")
            }
            startActivityForResult(intent, REQUEST_SAVE_FILE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SAVE_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.generarReportePdf(adapter.asistencias, uri, nombreReporte!!, requireContext())
            }
        }
    }


}
