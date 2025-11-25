package com.example.login.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.model.Videojuego
import com.example.login.repository.VideojuegoRepository
import com.example.login.viewmodel.CatalogoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BackofficeViewModel (
    private val repo: VideojuegoRepository = VideojuegoRepository()
) : ViewModel() {
    private val _videojuegos = MutableStateFlow<List<Videojuego>>(emptyList())
    val videojuegos: StateFlow<List<Videojuego>> = _videojuegos
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun cargarVideojuegos(context: Context) {
        viewModelScope.launch {
            _loading.value = true
            val list = repo.obtenerVideojuego(context)
            _videojuegos.value = list
            _loading.value = false
        }
    }
    fun onEditarVideojuego(videojuego: Videojuego) {
        println("Backoffice: Solicitud de edición de el producto ${videojuego.nombre}")
    }
    fun onEliminarVideojuego(videojuego: Videojuego, context: android.content.Context) {
        viewModelScope.launch {
            repo.eliminarVideojuego(videojuego)

            cargarVideojuegos(context)
            println("Backoffice: Producto ${videojuego.nombre} eliminado, recargando lista de productos")
        }
    }
    fun onVerVideojuego(videojuego: Videojuego) {
        println("Backoffice: Solicitud para ver detalles de el producto ${videojuego.nombre}")
    }
}
