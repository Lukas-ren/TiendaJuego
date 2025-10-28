package com.example.login.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.navigation.NavHostController


@Composable
fun HomeScreen(navController: NavHostController, email: String?) {
    LaunchedEffect(Unit) {
        // Espera 3 segundos para que el usuario vea el mensaje
        delay(3000)

        // Luego, navega a la pantalla de catálogo
        navController.navigate("catalogo") {
            popUpTo("home/{email}") { inclusive = true }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido 👋", style = MaterialTheme.typography.titleLarge)
        Text("Has iniciado sesión como: $email")
    }
}

