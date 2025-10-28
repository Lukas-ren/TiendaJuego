package com.example.login.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.model.Videojuego
import com.example.login.repository.VideojuegoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogoViewModel (
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

    fun buscarVideojuegoPorId(id: Int): Videojuego? {
        return _videojuegos.value.find { it.id == id }
    }
}