package com.example.stingtechsolution.screen

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun HerramientasScreen(
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    var bateria by remember {
        mutableStateOf<Int?>(null)
    }

    var mensajeVibracion by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Herramientas del dispositivo",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Consulta recursos del teléfono donde " +
                    "se ejecuta StingTech."
        )

        HorizontalDivider()

        Text(
            text = "1. Estado de batería",
            style = MaterialTheme.typography.titleLarge
        )

        Button(
            onClick = {
                val manager = context.getSystemService(
                    Context.BATTERY_SERVICE
                ) as BatteryManager

                val resultado = manager.getIntProperty(
                    BatteryManager.BATTERY_PROPERTY_CAPACITY
                )

                bateria = resultado.takeIf {
                    it in 0..100
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar batería")
        }

        AnimatedVisibility(
            visible = bateria != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    Text("Batería del dispositivo")

                    Text(
                        text = "${bateria ?: 0}%",
                        style =
                            MaterialTheme.typography.headlineLarge
                    )

                    LinearProgressIndicator(
                        progress = {
                            (bateria ?: 0) / 100f
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (bateria == null) {
            Text(
                "Presiona el botón para consultar la batería."
            )
        }

        HorizontalDivider()

        Text(
            text = "2. Prueba de vibración",
            style = MaterialTheme.typography.titleLarge
        )

        Button(
            onClick = {
                val vibrator: Vibrator =
                    if (Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.S
                    ) {
                        val manager =
                            context.getSystemService(
                                Context.VIBRATOR_MANAGER_SERVICE
                            ) as VibratorManager

                        manager.defaultVibrator
                    } else {
                        @Suppress("DEPRECATION")
                        (context.getSystemService(
                            Context.VIBRATOR_SERVICE
                        ) as Vibrator)
                    }

                if (vibrator.hasVibrator()) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            200L,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )

                    mensajeVibracion =
                        "Se solicitó una vibración de prueba."
                } else {
                    mensajeVibracion =
                        "Este dispositivo no dispone de vibrador."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Probar vibración")
        }

        AnimatedVisibility(
            visible = mensajeVibracion.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(mensajeVibracion)
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
