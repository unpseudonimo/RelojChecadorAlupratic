package com.example.relojchecadoralupratic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InicioViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esto es el fragmento de Inicio"
    }
    val text: LiveData<String> = _text
}