
package com.example.stingtechsolution.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stingtechsolution.data.database.StingTechDatabase
import com.example.stingtechsolution.data.repository.UsuarioRepository
import kotlinx.coroutines.launch
import com.example.stingtechsolution.data.entity.UsuarioEntity

data class AuthUiState(
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val confirmarPassword: String = "",
    val errorNombre: String = "",
    val errorCorreo: String = "",
    val errorPassword: String = "",
    val errorConfirmacion: String = "",
    val mensaje: String = "",
    val cargando: Boolean = false
)

class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(
        StingTechDatabase.obtenerDatabase(
            application
        ).usuarioDao()
    )

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

    var usuarioActual by mutableStateOf<UsuarioEntity?>(null)
        private set

    fun cerrarSesion() {
        usuarioActual = null
        limpiarFormulario()
    }

    fun limpiarFormulario() {
        uiState = AuthUiState()
    }

    private fun validarCampos(
        esRegistro: Boolean
    ): Boolean {

        val estado = uiState

        val errorNombre =
            if (esRegistro && estado.nombre.isBlank())
                "Ingresa tu nombre"
            else ""

        val errorCorreo = when {
            estado.correo.isBlank() ->
                "Ingresa tu correo"

            !Patterns.EMAIL_ADDRESS.matcher(
                estado.correo.trim()
            ).matches() ->
                "Correo electrónico inválido"

            else -> ""
        }

        val errorPassword = when {
            estado.password.isBlank() ->
                "Ingresa tu contraseña"

            esRegistro && estado.password.length < 6 ->
                "Mínimo 6 caracteres"

            else -> ""
        }

        val errorConfirmacion = when {
            !esRegistro -> ""

            estado.confirmarPassword.isBlank() ->
                "Confirma tu contraseña"

            estado.password != estado.confirmarPassword ->
                "Las contraseñas no coinciden"

            else -> ""
        }

        uiState = estado.copy(
            errorNombre = errorNombre,
            errorCorreo = errorCorreo,
            errorPassword = errorPassword,
            errorConfirmacion = errorConfirmacion,
            mensaje = ""
        )

        return listOf(
            errorNombre,
            errorCorreo,
            errorPassword,
            errorConfirmacion
        ).all { it.isEmpty() }
    }

    fun registrar(onExito: () -> Unit) {

        if (uiState.cargando) return
        if (!validarCampos(esRegistro = true)) return

        val nombre = uiState.nombre
        val correo = uiState.correo
        val password = uiState.password

        uiState = uiState.copy(cargando = true)

        viewModelScope.launch {
            try {
                val registrado = repository.registrar(
                    nombre,
                    correo,
                    password
                )

                if (registrado) {
                    limpiarFormulario()
                    onExito()
                } else {
                    uiState = uiState.copy(
                        errorCorreo = "Este correo ya está registrado"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    mensaje = if (
                        e is SQLiteConstraintException ||
                        e.cause is SQLiteConstraintException
                    ) {
                        "Este correo ya está registrado"
                    } else {
                        "No se pudo guardar el usuario"
                    }
                )
            } finally {
                uiState = uiState.copy(cargando = false)
            }
        }
    }

    fun ingresar(onExito: () -> Unit) {

        if (uiState.cargando) return
        if (!validarCampos(esRegistro = false)) return

        val correo = uiState.correo
        val password = uiState.password

        uiState = uiState.copy(cargando = true)

        viewModelScope.launch {
            try {
                val correcto = repository.ingresar(
                    correo,
                    password
                )

                if (correcto) {
                    usuarioActual = repository.obtenerUsuario(correo)
                    limpiarFormulario()
                    onExito()
                } else {
                    uiState = uiState.copy(
                        mensaje = "Correo o contraseña incorrectos"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    mensaje = "No se pudo verificar el usuario"
                )
            } finally {
                uiState = uiState.copy(cargando = false)
            }
        }
    }
}
