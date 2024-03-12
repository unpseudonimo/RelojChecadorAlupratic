package com.example.relojchecadoralupratic.models

data class Reporte(val asistencias: List<Asistencia>, val nombre_reporte: String)
data class ArchivosZipResponse(val archivosZip: List<String>)
