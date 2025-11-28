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
import kotlinx.coroutines.delay

@Composable
fun CompraExitosaScreen(navController: NavController, viewModel: CompraExitosaViewModel) {
    val detalles by viewModel.detallesPedido.collectAsState()
    val codigosCanje by viewModel.codigosCanje.collectAsState()
    val navegarInicio by viewModel.navegarInicio.observeAsState(false)
    val navegarTienda by viewModel.navegarTienda.observeAsState(false)

    LaunchedEffect(Unit) {
        val cantidad = detalles?.numeroArticulos ?: 0
        if (cantidad > 0) {
            viewModel.inicializarCodigos(cantidad = cantidad)
        }
    }
        LaunchedEffect(key1=navegarInicio, key2=navegarTienda) {
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
    Scaffold { padding ->
        24
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
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
                text = "Tus códigos de canje (uno por juego):",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))
            codigosCanje.forEachIndexed { index, code ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Código para Juego #${index + 1}:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = code,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))


            detalles?.let {
                Text("Número de pedido: ${it.idPedido}")
                Text("Artículos: ${it.numeroArticulos}")
                Text("Método de pago: ${it.metodoPago}")
                Text("Total: $${String.format("%.2f", it.totalCompra)}")
            }


            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
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
