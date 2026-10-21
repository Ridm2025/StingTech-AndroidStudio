package com.example.stingtechsolution.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.stingtechsolution.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onVolver: () -> Unit
) {
    val estado = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text("Regístrate para solicitar reparaciones.")

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::actualizarNombre,
            label = { Text("Nombre completo") },
            isError = estado.errorNombre.isNotEmpty(),
            supportingText = {
                if (estado.errorNombre.isNotEmpty()) {
                    Text(estado.errorNombre)
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::actualizarCorreo,
            label = { Text("Correo electrónico") },
            isError = estado.errorCorreo.isNotEmpty(),
            supportingText = {
                if (estado.errorCorreo.isNotEmpty()) {
                    Text(estado.errorCorreo)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.password,
            onValueChange = viewModel::actualizarPassword,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errorPassword.isNotEmpty(),
            supportingText = {
                if (estado.errorPassword.isNotEmpty()) {
                    Text(estado.errorPassword)
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.confirmarPassword,
            onValueChange = viewModel::actualizarConfirmacion,
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errorConfirmacion.isNotEmpty(),
            supportingText = {
                if (estado.errorConfirmacion.isNotEmpty()) {
                    Text(estado.errorConfirmacion)
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.validarRegistro() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        if (estado.mensaje.isNotEmpty()) {
            Text(
                text = estado.mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
