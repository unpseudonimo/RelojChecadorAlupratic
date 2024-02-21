package com.example.relojchecadoralupratic.models

data class ApiEmpleadoRes(
    val uid: Int,
    val userid: String,
    val name: String,
    val password: String?,
    val role: Int,
    val cardno: Int?
)
