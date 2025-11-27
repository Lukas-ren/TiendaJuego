package com.example.login.views

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.login.model.Videojuego
import com.example.login.viewmodel.CatalogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(navController: NavController, viewModel: CatalogoViewModel) {
    val context = LocalContext.current
    val videojuegos by viewModel.videojuegos.collectAsState()
    val loading by viewModel.loading.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.cargarVideojuegos(
            context,
            usarRemoto = true,
            baseUrl = "https://rawg.io/apidocs"  
        )
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("Catálogo") }
        )
    }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return@Box
            }

            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = videojuegos, key = { it.id }) { videojuego ->
                    VideojuegoCard(videojuego = videojuego, onClick = {
                        navController.navigate("detalle/${videojuego.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun VideojuegoCard(videojuego: Videojuego, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            val painter = rememberAsyncImagePainter(videojuego.imagen)
            Image(painter = painter, contentDescription = videojuego.nombre, modifier = Modifier.size(80.dp), contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(text = videojuego.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "Plataforma: ${videojuego.plataforma}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "$${videojuego.precio}", style = MaterialTheme.typography.bodyMedium)
                videojuego.genero?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = it, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                }
            }
        }
    }
}
