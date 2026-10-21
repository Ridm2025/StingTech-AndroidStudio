package com.example.stingtechsolution.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreEquipo: String,
    val tipoEquipo: String,
    val descripcion: String,
    val estado: String = "Pendiente"
)
