package com.example.relojchecadoralupratic.models

data class EmpleadoDetalle(
    val Cardno: String,
    val UID: Int,
    val id: String,
    val name: String,
    val rol: String,
    val asistencias: List<Asistencia>
)
