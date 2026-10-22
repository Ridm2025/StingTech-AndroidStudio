package com.example.stingtechsolution.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.stingtechsolution.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onVolver: () -> Unit,
    onRegistro: () -> Unit,
    onIngresar: () -> Unit
) {

    val estado = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text("Bienvenido a StingTech")

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

        Button(
            onClick = {
                viewModel.ingresar(
                    onExito = onIngresar
                )
            },
            enabled = !estado.cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (estado.cargando)
                    "Verificando..."
                else
                    "Ingresar"
            )
        }

        if (estado.mensaje.isNotEmpty()) {
            Text(
                text = estado.mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }

        TextButton(
            onClick = onRegistro,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
