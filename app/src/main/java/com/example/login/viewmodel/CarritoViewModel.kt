package com.example.login.viewmodel

import androidx.lifecycle.ViewModel
import com.example.login.model.Carrito
import com.example.login.model.Videojuego
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CarritoViewModel : ViewModel() {

    private val _carrito = MutableStateFlow<List<Carrito>>(emptyList())
    val carrito: StateFlow<List<Carrito>> = _carrito

    val total: Double
        get() = _carrito.value.sumOf { it.subtotal }

    fun agregarProducto(item: Carrito) {
        _carrito.update { current ->
            val existente = current.find { it.id == item.id }
            if (existente != null) {
                current.map {
                    if (it.id == item.id) it.copy(cantidad = it.cantidad + 1) else it
                }
            } else {
                current + item
            }
        }
    }

    fun quitarProducto(id: Int) {
        _carrito.update { current ->
            current.filterNot { it.id == id }
        }
    }

    fun agregarSiNoExiste(videojuego: Carrito) {
        _carrito.update { current ->
            val existe = current.any { it.id == videojuego.id }
            if (!existe) {
                current + videojuego.copy(cantidad = 1)
            } else {
                current
            }
        }
    }

    fun disminuirCantidad(id: Int) {
        _carrito.update { current ->
            current.mapNotNull {
                if (it.id == id) {
                    if (it.cantidad > 1) it.copy(cantidad = it.cantidad - 1) else null
                } else it
            }
        }
    }

    fun vaciarCarrito() {
        _carrito.value = emptyList()
    }
}