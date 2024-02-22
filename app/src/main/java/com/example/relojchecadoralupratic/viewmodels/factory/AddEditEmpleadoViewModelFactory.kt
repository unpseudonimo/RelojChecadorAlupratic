package com.example.relojchecadoralupratic.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.relojchecadoralupratic.network.ApiService
import com.example.relojchecadoralupratic.viewmodels.AddEditEmpleadoViewModel

class AddEditEmpleadoViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditEmpleadoViewModel::class.java)) {
            return AddEditEmpleadoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
