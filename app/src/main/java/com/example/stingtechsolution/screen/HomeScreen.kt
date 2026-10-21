package com.example.stingtechsolution.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    cantidadSolicitudes: Int,
    onNuevaSolicitud: () -> Unit,
    onMisSolicitudes: () -> Unit,
    onHerramientas: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "StingTech",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Panel de servicios técnicos",
            style = MaterialTheme.typography.titleMedium
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Solicitudes registradas")
                Text(
                    text = "$cantidadSolicitudes",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        Button(
            onClick = onNuevaSolicitud,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva solicitud")
        }

        OutlinedButton(
            onClick = onMisSolicitudes,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mis solicitudes")
        }

        OutlinedButton(
            onClick = onHerramientas,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Herramientas del dispositivo")
        }
    }
}
