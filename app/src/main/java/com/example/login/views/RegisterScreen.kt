package com.example.login.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro", style = MaterialTheme.typography.titleLarge, color = Color.White)

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") })

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { viewModel.registrar(nombre, email, password) }) {
            Text("Registrar")
        }

        Text(viewModel.mensaje.value, modifier = Modifier.padding(top = 10.dp), color = Color.White)

        TextButton(onClick = { navController.navigate("login") }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}