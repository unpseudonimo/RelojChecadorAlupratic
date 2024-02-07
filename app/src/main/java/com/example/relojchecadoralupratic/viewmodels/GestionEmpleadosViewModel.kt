package com.example.relojchecadoralupratic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GestionEmpleadosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento de gestion de empleados"
    }
    val text: LiveData<String> = _text
}