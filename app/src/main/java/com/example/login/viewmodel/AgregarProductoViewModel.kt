package com.example.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.model.Videojuego
import com.example.login.repository.VideojuegoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AgregarProductoState(
    val nombre: String = "",
    val genero: String = "",
    val plataforma: String = "",
    val precio: String = "",
    val imagen: String = "",
    val stock: String = "",
    val codigo: String = "",
    val isLoading: Boolean = false,
    val saveSuccess: Boolean = false,
    val errorMessage: String? = null
)

class AgregarProductoViewModel(
    private val repo: VideojuegoRepository = VideojuegoRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(AgregarProductoState())
    val state: StateFlow<AgregarProductoState> = _state


    fun onNombreChange(nombre: String) {
        _state.value = _state.value.copy(nombre = nombre, saveSuccess = false, errorMessage = null)
    }

    fun onGeneroChange(genero: String) {
        _state.value = _state.value.copy(genero = genero, saveSuccess = false, errorMessage = null)
    }

    fun onPrecioChange(precio: String) {
        _state.value = _state.value.copy(precio = precio, saveSuccess = false, errorMessage = null)
    }

    fun onStockChange(stock: String) {
        _state.value = _state.value.copy(stock = stock, saveSuccess = false, errorMessage = null)
    }

    fun onPlataformaChange(plataforma: String) {
        _state.value = _state.value.copy(plataforma = plataforma, saveSuccess = false, errorMessage = null)
    }

    fun onImagenChange(imagen: String) {
        _state.value = _state.value.copy(imagen = imagen, saveSuccess = false, errorMessage = null)
    }

    fun guardarProducto() {
        val currentState = _state.value

        val precio = currentState.precio.toIntOrNull()
        val stock = currentState.stock.toIntOrNull()

        if (precio == null || stock == null ) {
            _state.value = currentState.copy(errorMessage = "Por favor, verifica los campos numéricos (Precio, Stock, Código).")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, errorMessage = null)

            try {
                val nuevoId = (0..99999).random()

                val nuevoVideojuego = Videojuego(
                    id = nuevoId, // Se asigna aquí o en el repositorio
                    nombre = currentState.nombre,
                    genero = currentState.genero.ifEmpty { null },
                    plataforma = currentState.plataforma,
                    precio = precio,
                    imagen = currentState.imagen,
                    stock = stock
                )

                repo.agregarVideojuego(nuevoVideojuego)

                _state.value = AgregarProductoState(saveSuccess = true)

            } catch (e: Exception) {
                _state.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Error al guardar el producto: ${e.message}"
                )
            }
        }
    }
}