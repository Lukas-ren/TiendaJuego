package com.example.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.login.viewmodel.BackofficeViewModel
import com.example.login.viewmodel.CatalogoViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackOfficeScreen(
    navController: NavController,
    backofficeViewModel: BackofficeViewModel = viewModel()
) {
    val context = LocalContext.current
    val videojuegos = backofficeViewModel.videojuegos.collectAsState()
    LaunchedEffect(Unit) {
        backofficeViewModel.cargarVideojuegos(context)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración") },
                actions = {
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("Volver", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (videojuegos.value.isEmpty()) {
                Text(
                    text = "No hay productos para mostrar",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(videojuegos.value) { juego ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(text = "ID: ${juego.id}", style = MaterialTheme.typography.labelSmall)
                                Text(text = juego.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Stock: ${juego.stock}", style = MaterialTheme.typography.bodySmall)
                                Text(text = "Precio: $${juego.precio}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Género: ${juego.genero}", style = MaterialTheme.typography.bodySmall)

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(onClick = { /* No funcional */ }) {
                                        Text("Editar")
                                    }
                                    OutlinedButton(onClick = { /* No funcional */ }) {
                                        Text("Eliminar")
                                    }
                                    OutlinedButton(onClick = { /* No funcional */ }) {
                                        Text("Ver")
                                    }
                                }
                                val painter = rememberAsyncImagePainter(juego.imagen)
                                Image(painter = painter, contentDescription = juego.nombre, modifier = Modifier.size(80.dp), contentScale = ContentScale.Crop)
                            }
                        }
                    }
                }
            }
        }
    }
}
