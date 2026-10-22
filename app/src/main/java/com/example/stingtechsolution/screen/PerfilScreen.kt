package com.example.stingtechsolution.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PerfilScreen(
    nombre: String?,
    correo: String?,
    onVolver: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val azul = Color(0xFF0A2E5C)
    val naranja = Color(0xFFE86A2F)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = azul,
            modifier = Modifier.fillMaxWidth()
        )

        Surface(
            shape = CircleShape,
            color = azul,
            modifier = Modifier.size(92.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = nombre
                        ?.trim()
                        ?.take(1)
                        ?.uppercase()
                        ?: "I",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
        }

        Text(
            text = nombre ?: "Invitado",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement =
                    Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Información personal",
                    style = MaterialTheme.typography.titleMedium,
                    color = azul
                )

                Text("Nombre completo")
                Text(
                    text = nombre ?: "Invitado",
                    fontWeight = FontWeight.Bold
                )

                HorizontalDivider()

                Text("Correo electrónico")
                Text(
                    text = correo ?: "Sin cuenta registrada",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (correo == null) {
            Text(
                text = "Estás utilizando el modo demostración.",
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = naranja
            )
        ) {
            Text("Salir al inicio")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al menú")
        }
    }
}
