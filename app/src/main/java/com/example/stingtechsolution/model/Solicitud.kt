package com.example.stingtechsolution.model

data class Solicitud(
    val id: Int,
    val nombreEquipo: String,
    val tipoEquipo: String,
    val descripcion: String,
    val estado: String = "Pendiente"
)
