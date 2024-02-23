package com.example.relojchecadoralupratic.models

data class EmpleadoResponse(
    val uid: Int,
    val userid: String,
    val name: String,
    val role: Int,
    val cardno: Int?
)

data class Empleado(
    val name: String,
    val cardno: String,
    val rol: String,
    val password: String
)