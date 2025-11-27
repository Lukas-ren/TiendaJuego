package com.example.login.views
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.login.model.DetallePedido
import com.example.login.viewmodel.CarritoViewModel
import com.example.login.viewmodel.CompraExitosaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel(),
    compraExitosaViewModel: CompraExitosaViewModel = viewModel()
) {
    val carrito = carritoViewModel.carrito.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🛒 Carrito de Compras") },
                actions = {
                    TextButton(onClick = { carritoViewModel.vaciarCarrito() }) {
                        Text("Vaciar", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total: $${carritoViewModel.total}")
                    Button(onClick = {
                        val carritoActual = carritoViewModel.carrito.value

                        if (carritoActual.isNullOrEmpty()) {
                            navController.navigate("compraRechazada")
                        } else {
                            val total = carritoViewModel.total
                            val detalles = DetallePedido(
                                idPedido = "PED-${System.currentTimeMillis()}",
                                totalCompra = total,
                                numeroArticulos = carritoActual.size,
                                metodoPago = "Tarjeta"
                            )
                            compraExitosaViewModel.setDetallesPedido(detalles)
                            carritoViewModel.vaciarCarrito()
                            navController.navigate("compraExitosa")
                        }
                    }) {
                        Text(text = "Finalizar compra")
                    }
                }
            }
        }
    ) { padding ->
        if (carrito.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío 🛍️")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(carrito.value) { videojuego ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = videojuego.imagen,
                                contentDescription = videojuego.nombre,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(videojuego.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Precio: $${videojuego.precio}")
                                Text("Subtotal: $${videojuego.subtotal}")
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { carritoViewModel.disminuirCantidad(videojuego.id) }) {
                                    Text("-")
                                }
                                Text("${videojuego.cantidad}", modifier = Modifier.padding(horizontal = 4.dp))
                                Button(
                                    onClick = {
                                        carritoViewModel.agregarProducto(
                                            com.example.login.model.Carrito(
                                                id = videojuego.id,
                                                nombre = videojuego.nombre,
                                                precio = videojuego.precio,
                                                imagen = videojuego.imagen,
                                                cantidad = videojuego.cantidad
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = "+")
                                }
                                IconButton(onClick = { carritoViewModel.quitarProducto(videojuego.id) }) {
                                    Text("🗑️")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
