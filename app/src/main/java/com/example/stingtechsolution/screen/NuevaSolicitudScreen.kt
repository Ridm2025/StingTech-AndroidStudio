package com.example.stingtechsolution.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stingtechsolution.viewmodel.SolicitudViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaSolicitudScreen(
    viewModel: SolicitudViewModel,
    onGuardado: () -> Unit,
    onVolver: () -> Unit
) {
    val estado = viewModel.uiState
    val scope = rememberCoroutineScope()
    var menuAbierto by remember { mutableStateOf(false) }

    val tipos = listOf(
        "Notebook",
        "Computador de escritorio",
        "All in One",
        "Otro"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nueva solicitud",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = estado.nombreEquipo,
            onValueChange = viewModel::actualizarNombre,
            label = { Text("Nombre del equipo") },
            placeholder = { Text("Ej: Notebook Lenovo") },
            isError = estado.errorNombre.isNotEmpty(),
            supportingText = {
                if (estado.errorNombre.isNotEmpty()) {
                    Text(estado.errorNombre)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = menuAbierto,
            onExpandedChange = { menuAbierto = it }
        ) {
            OutlinedTextField(
                value = estado.tipoEquipo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de equipo") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = menuAbierto
                    )
                },
                isError = estado.errorTipo.isNotEmpty(),
                supportingText = {
                    if (estado.errorTipo.isNotEmpty()) {
                        Text(estado.errorTipo)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable
                    )
            )

            ExposedDropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            viewModel.actualizarTipo(tipo)
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = estado.descripcion,
            onValueChange = viewModel::actualizarDescripcion,
            label = { Text("Descripción del problema") },
            placeholder = {
                Text("Describe qué le ocurre al equipo")
            },
            minLines = 3,
            isError = estado.errorDescripcion.isNotEmpty(),
            supportingText = {
                if (estado.errorDescripcion.isNotEmpty()) {
                    Text(estado.errorDescripcion)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                scope.launch {
                    if (viewModel.guardarSolicitud()) {
                        onGuardado()
                    }
                }
            },
            enabled = !estado.guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (estado.guardando)
                    "Guardando..."
                else
                    "Guardar solicitud"
            )
        }
    }
}
