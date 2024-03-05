package com.example.relojchecadoralupratic.models

data class AsistenciaResponse(
    val id: Int, // Ejemplo de propiedad "id"
    val empleadoId: Int,
    val fecha: String,
    val horaEntrada: String,
    val horaSalida: String? // El signo de interrogación indica que puede ser nulo
)

data class Asistencia(
    val registro: String,
    val UID: Int,
    val tipo_acceso: String,
    val fecha_registro: String,
    val hora_registro: String,
    val id_empleado: String,
    val nombre_empleado: String
)
