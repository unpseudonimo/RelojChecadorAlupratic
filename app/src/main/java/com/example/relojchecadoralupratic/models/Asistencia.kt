package com.example.relojchecadoralupratic.models

data class AsistenciaResponse(
    val id: Int, // Ejemplo de propiedad "id"
    val empleadoId: Int,
    val fecha: String,
    val horaEntrada: String,
    val horaSalida: String? // El signo de interrogación indica que puede ser nulo
)

data class Asistencia(
    val entrada: String,
    val UID: Int,
    val acceso: String,
    val fecha: String,
    val hora: String,
    val id: String,
    val name: String
)
