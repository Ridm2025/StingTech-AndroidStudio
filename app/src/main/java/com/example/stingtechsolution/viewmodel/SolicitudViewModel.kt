package com.example.stingtechsolution.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.stingtechsolution.data.database.StingTechDatabase
import com.example.stingtechsolution.data.repository.SolicitudRepository

data class SolicitudUiState(
    val nombreEquipo: String = "",
    val tipoEquipo: String = "",
    val descripcion: String = "",
    val errorNombre: String = "",
    val errorTipo: String = "",
    val errorDescripcion: String = "",
    val guardando: Boolean = false
)

class SolicitudViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        StingTechDatabase.obtenerDatabase(application)

    private val repository =
        SolicitudRepository(database.solicitudDao())

    val solicitudes = repository.solicitudes

    var uiState by mutableStateOf(SolicitudUiState())
        private set

    fun actualizarNombre(valor: String) {
        uiState = uiState.copy(
            nombreEquipo = valor,
            errorNombre = ""
        )
    }

    fun actualizarTipo(valor: String) {
        uiState = uiState.copy(
            tipoEquipo = valor,
            errorTipo = ""
        )
    }

    fun actualizarDescripcion(valor: String) {
        uiState = uiState.copy(
            descripcion = valor,
            errorDescripcion = ""
        )
    }

    suspend fun guardarSolicitud(): Boolean {

        if (uiState.guardando) return false

        val errorNombre = if (uiState.nombreEquipo.isBlank()) {
            "Debes ingresar el nombre del equipo"
        } else ""

        val errorTipo = if (uiState.tipoEquipo.isBlank()) {
            "Debes seleccionar un tipo de equipo"
        } else ""

        val errorDescripcion = if (uiState.descripcion.isBlank()) {
            "Debes describir el problema"
        } else ""

        uiState = uiState.copy(
            errorNombre = errorNombre,
            errorTipo = errorTipo,
            errorDescripcion = errorDescripcion
        )

        if (
            errorNombre.isNotEmpty() ||
            errorTipo.isNotEmpty() ||
            errorDescripcion.isNotEmpty()
        ) {
            return false
        }

        uiState = uiState.copy(guardando = true)

        return try {
            repository.insertarSolicitud(
                nombreEquipo = uiState.nombreEquipo.trim(),
                tipoEquipo = uiState.tipoEquipo,
                descripcion = uiState.descripcion.trim()
            )

            limpiarFormulario()
            true
        } finally {
            uiState = uiState.copy(guardando = false)
        }
    }

    fun limpiarFormulario() {
        uiState = SolicitudUiState()
    }
}
