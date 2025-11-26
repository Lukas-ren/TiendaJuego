package com.example.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.login.viewmodel.CatalogoViewModel
import com.example.login.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleVideojuegoScreen(videojuegoId: Int,
                            viewModel: CatalogoViewModel,
                            navController: NavController,
                            CarritoViewModel: CarritoViewModel = viewModel()) {

    val videojuego = remember { viewModel.buscarVideojuegoPorId(videojuegoId) }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(videojuego?.nombre ?: "Detalle") })
    }) { padding ->
        videojuego?.let { v ->
            Column(modifier = Modifier
                .padding(padding)
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                val painter = rememberAsyncImagePainter(v.imagen)
                Image(painter = painter, contentDescription = v.nombre, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp))
                Text(text = v.nombre, style = MaterialTheme.typography.titleLarge)
                Text(text = "$${v.precio}", style = MaterialTheme.typography.titleMedium)
                Text(text = v.genero ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = v.plataforma ?: "", style = MaterialTheme.typography.bodyMedium)
                Button(
                    onClick = {
                        CarritoViewModel.agregarSiNoExiste(
                            com.example.login.model.Carrito(
                                id = v.id,
                                nombre = v.nombre,
                                precio = v.precio,
                                imagen = v.imagen,
                                codigo = v.codigo,
                                cantidad = 1
                            )
                        )
                        navController.navigate("carrito")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Agregar al carrito 🛒")
                }

                OutlinedButton(
                    onClick = { navController.navigate("carrito") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver carrito")
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Producto no encontrado")
        }
    }
}
