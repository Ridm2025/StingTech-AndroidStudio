package com.example.stingtechsolution.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    cantidadSolicitudes: Int,
    nombreUsuario: String?,
    onNuevaSolicitud: () -> Unit,
    onMisSolicitudes: () -> Unit,
    onHerramientas: () -> Unit,
    onPerfil: () -> Unit
) {
    val azul = Color(0xFF0A2E5C)
    val naranja = Color(0xFFE86A2F)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Bienvenido,",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = nombreUsuario ?: "Invitado",
                    style = MaterialTheme.typography.headlineMedium,
                    color = azul,
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedButton(onClick = onPerfil) {
                Text("Mi perfil")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = azul
            )
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "StingTech",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "¿Necesitas reparar tu computador?",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Registra una solicitud y lleva " +
                            "el seguimiento de tu equipo.",
                    color = Color.White
                )

                Button(
                    onClick = onNuevaSolicitud,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = naranja
                    )
                ) {
                    Text("Nueva solicitud")
                }
            }
        }

        Text(
            text = "Resumen de servicios",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onMisSolicitudes() },
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {
                Text("Solicitudes registradas")

                Text(
                    text = "$cantidadSolicitudes",
                    style = MaterialTheme.typography.headlineLarge,
                    color = naranja,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Ver historial de reparaciones",
                    color = azul
                )
            }
        }

        Text(
            text = "Accesos rápidos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = azul
        )

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

        OutlinedButton(
            onClick = onPerfil,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Información de mi usuario")
        }
    }
}
