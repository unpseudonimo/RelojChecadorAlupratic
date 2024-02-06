package com.example.relojchecadoralupratic.ui.fragments.reportes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.relojchecadoralupratic.databinding.FragmentReportesBinding
import com.example.relojchecadoralupratic.viewmodels.ReportesViewModel

class ReportesFragment : Fragment() {

    private var _binding: FragmentReportesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reportesViewModel =
            ViewModelProvider(this).get(ReportesViewModel::class.java)

        _binding = FragmentReportesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        reportesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}