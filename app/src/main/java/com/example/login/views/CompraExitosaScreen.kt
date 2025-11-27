package com.example.login.views

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.login.model.DetallePedido
import com.example.login.model.Videojuego
import com.example.login.viewmodel.CatalogoViewModel
import com.example.login.viewmodel.CompraExitosaViewModel

@Composable
fun CompraExitosaScreen(
    navController: NavController,
    viewModel: CompraExitosaViewModel = viewModel()
) {
    val detalles by viewModel.detallesPedido.observeAsState()
    val codigoCanje by viewModel.codigoCanje.collectAsState()
    val navegarInicio by viewModel.navegarInicio.observeAsState(false)
    val navegarTienda by viewModel.navegarTienda.observeAsState(false)

    LaunchedEffect(navegarInicio, navegarTienda) {
        when {
            navegarInicio -> {
                viewModel.navegacionCompletada()
                navController.navigate("inicio") {
                    popUpTo("carrito") { inclusive = true }
                }
            }
            navegarTienda -> {
                viewModel.navegacionCompletada()
                navController.navigate("catalogo") {
                    popUpTo("carrito") { inclusive = true }
                }
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Compra exitosa",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("¡Compra realizada con éxito!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tu código de canje:",
                style = MaterialTheme.typography.titleMedium
            )
            Card{
                Text(
                    text = codigoCanje,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }

            detalles?.let {
                Text("Número de pedido: ${it.idPedido}")
                Text("Artículos: ${it.numeroArticulos}")
                Text("Método de pago: ${it.metodoPago}")
                Text("Total: $${String.format("%.2f", it.totalCompra)}")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = { viewModel.navegarAInicio() }) {
                    Text("Salir de la aplicación")
                }
                Button(onClick = { viewModel.navegarATienda() }) {
                    Text("Seguir comprando")
                }
            }
        }
    }
}