package com.example.relojchecadoralupratic.models

data class AsistenciaRegistro(
    val id: Int, // Ejemplo de propiedad "id"
    val empleadoId: Int,
    val fecha: String,
    val horaEntrada: String,
    val horaSalida: String? // El signo de interrogación indica que puede ser nulo
)
