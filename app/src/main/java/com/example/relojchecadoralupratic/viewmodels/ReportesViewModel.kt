package com.example.relojchecadoralupratic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esto es el fragmento de reportes"
    }
    val text: LiveData<String> = _text
}