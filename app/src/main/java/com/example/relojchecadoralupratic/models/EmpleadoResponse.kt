package com.example.relojchecadoralupratic.models

data class EmpleadoResponse(
    val Cardno: String,
    val UID: Int,
    val id: String,
    val name: String,
    val rol: String
)

data class Empleado(
    val name: String,
    val cardno: String,
    val rol: String,
    val password: String
)