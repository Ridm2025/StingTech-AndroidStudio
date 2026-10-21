package com.example.stingtechsolution.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stingtechsolution.model.Solicitud

@Composable
fun MisSolicitudesScreen(
    solicitudes: List<Solicitud>,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mis solicitudes",
            style = MaterialTheme.typography.headlineMedium
        )

        if (solicitudes.isEmpty()) {
            Text("Todavía no tienes solicitudes registradas.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = solicitudes,
                    key = { it.id }
                ) { solicitud ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Solicitud #${solicitud.id}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("Equipo: ${solicitud.nombreEquipo}")
                            Text("Tipo: ${solicitud.tipoEquipo}")
                            Text("Problema: ${solicitud.descripcion}")
                            Text("Estado: ${solicitud.estado}")
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
