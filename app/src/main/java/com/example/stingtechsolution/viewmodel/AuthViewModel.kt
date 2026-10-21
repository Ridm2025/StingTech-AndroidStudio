package com.example.stingtechsolution.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class AuthUiState(
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val confirmarPassword: String = "",
    val errorNombre: String = "",
    val errorCorreo: String = "",
    val errorPassword: String = "",
    val errorConfirmacion: String = "",
    val mensaje: String = ""
)

class AuthViewModel : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun actualizarNombre(valor: String) {
        uiState = uiState.copy(
            nombre = valor,
            errorNombre = "",
            mensaje = ""
        )
    }

    fun actualizarCorreo(valor: String) {
        uiState = uiState.copy(
            correo = valor,
            errorCorreo = "",
            mensaje = ""
        )
    }

    fun actualizarPassword(valor: String) {
        uiState = uiState.copy(
            password = valor,
            errorPassword = "",
            mensaje = ""
        )
    }

    fun actualizarConfirmacion(valor: String) {
        uiState = uiState.copy(
            confirmarPassword = valor,
            errorConfirmacion = "",
            mensaje = ""
        )
    }

    fun limpiarFormulario() {
        uiState = AuthUiState()
    }

    fun validarLogin(): Boolean {
        val correo = uiState.correo.trim()

        val errorCorreo = when {
            correo.isEmpty() -> "Ingresa tu correo"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                "Correo electrónico inválido"
            else -> ""
        }

        val errorPassword = if (uiState.password.isBlank()) {
            "Ingresa tu contraseña"
        } else {
            ""
        }

        val valido = errorCorreo.isEmpty() &&
                errorPassword.isEmpty()

        uiState = uiState.copy(
            errorCorreo = errorCorreo,
            errorPassword = errorPassword,
            mensaje = if (valido)
                "Formulario válido. Falta verificar la cuenta en Room."
            else
                ""
        )

        return valido
    }

    fun validarRegistro(): Boolean {
        val correo = uiState.correo.trim()

        val errorNombre = if (uiState.nombre.isBlank()) {
            "Ingresa tu nombre"
        } else {
            ""
        }

        val errorCorreo = when {
            correo.isEmpty() -> "Ingresa tu correo"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                "Correo electrónico inválido"
            else -> ""
        }

        val errorPassword = when {
            uiState.password.isBlank() -> "Ingresa una contraseña"
            uiState.password.length < 6 ->
                "La contraseña debe tener al menos 6 caracteres"
            else -> ""
        }

        val errorConfirmacion = when {
            uiState.confirmarPassword.isBlank() ->
                "Confirma tu contraseña"
            uiState.password != uiState.confirmarPassword ->
                "Las contraseñas no coinciden"
            else -> ""
        }

        val valido = listOf(
            errorNombre,
            errorCorreo,
            errorPassword,
            errorConfirmacion
        ).all { it.isEmpty() }

        uiState = uiState.copy(
            errorNombre = errorNombre,
            errorCorreo = errorCorreo,
            errorPassword = errorPassword,
            errorConfirmacion = errorConfirmacion,
            mensaje = if (valido)
                "Formulario válido. Falta guardar la cuenta en Room."
            else
                ""
        )

        return valido
    }
}
