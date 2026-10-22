
package com.example.stingtechsolution.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stingtechsolution.screen.HomeScreen
import com.example.stingtechsolution.screen.LoginScreen
import com.example.stingtechsolution.screen.RegisterScreen
import com.example.stingtechsolution.screen.NuevaSolicitudScreen
import com.example.stingtechsolution.screen.MisSolicitudesScreen
import com.example.stingtechsolution.viewmodel.AuthViewModel
import com.example.stingtechsolution.viewmodel.SolicitudViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.stingtechsolution.screen.HerramientasScreen
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.stingtechsolution.R
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.stingtechsolution.screen.PerfilScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()
    val solicitudViewModel: SolicitudViewModel = viewModel()
    val solicitudes by solicitudViewModel.solicitudes.collectAsState(
        initial = emptyList()
    )

    NavHost(
        navController = navController,
        startDestination = "bienvenida"
    ) {
        composable("bienvenida") {
            PantallaBienvenida(
                onLogin = {
                    authViewModel.limpiarFormulario()
                    navController.navigate("login")
                },
                onRegistro = {
                    authViewModel.limpiarFormulario()
                    navController.navigate("registro")
                },
                onDemo = {
                    authViewModel.cerrarSesion()

                    navController.navigate("home") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(
                viewModel = authViewModel,

                onVolver = {
                    navController.popBackStack()
                },

                onRegistro = {
                    authViewModel.limpiarFormulario()
                    navController.navigate("registro")
                },

                onIngresar = {
                    navController.navigate("home") {
                        popUpTo("bienvenida") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("registro") {
            RegisterScreen(
                viewModel = authViewModel,

                onVolver = {
                    navController.popBackStack(
                        "bienvenida",
                        false
                    )
                },

                onRegistroExitoso = {
                    navController.navigate("login") {
                        popUpTo("bienvenida") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                cantidadSolicitudes = solicitudes.size,
                nombreUsuario = authViewModel.usuarioActual?.nombre,

                onNuevaSolicitud = {
                    solicitudViewModel.limpiarFormulario()
                    navController.navigate("nueva_solicitud")
                },

                onMisSolicitudes = {
                    navController.navigate("mis_solicitudes")
                },

                onHerramientas = {
                    navController.navigate("herramientas")
                },

                onPerfil = {
                    navController.navigate("perfil")
                }
            )
        }

        composable("nueva_solicitud") {
            NuevaSolicitudScreen(
                viewModel = solicitudViewModel,
                onGuardado = {
                    navController.popBackStack()
                },
                onVolver = {
                    solicitudViewModel.limpiarFormulario()
                    navController.popBackStack()
                }
            )
        }

        composable("mis_solicitudes") {
            MisSolicitudesScreen(
                solicitudes = solicitudes,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("herramientas") {
            HerramientasScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("perfil") {
            PerfilScreen(
                nombre = authViewModel.usuarioActual?.nombre,
                correo = authViewModel.usuarioActual?.correo,

                onVolver = {
                    navController.popBackStack()
                },

                onCerrarSesion = {
                    authViewModel.cerrarSesion()

                    navController.navigate("bienvenida") {
                        popUpTo("bienvenida") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun PantallaBienvenida(
    onLogin: () -> Unit,
    onRegistro: () -> Unit,
    onDemo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.logo_stingtech
            ),
            contentDescription = "Logo de StingTech Tech Solutions",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Servicio técnico de computadores",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Registra equipos, solicita reparaciones " +
                    "y consulta el estado de tus solicitudes."
        )

        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        OutlinedButton(
            onClick = onRegistro,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        HorizontalDivider()

        Text(
            text = "Acceso de demostración",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = onDemo,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Probar StingTech (demo)")
        }

    }
}
