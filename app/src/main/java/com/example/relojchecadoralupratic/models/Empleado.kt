package com.example.relojchecadoralupratic.models

data class Empleado(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val puesto: String,
    val salario: Double,
    val fechaContratacion: String
)
