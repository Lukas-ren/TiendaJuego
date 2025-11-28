package com.example.login.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login.model.DetallePedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class CompraExitosaViewModel : ViewModel() {
    private val _detallesPedido = MutableStateFlow<DetallePedido?>(null)
    val detallesPedido: StateFlow<DetallePedido?> = _detallesPedido
    private val _navegarInicio = MutableLiveData<Boolean>()
    val navegarInicio: LiveData<Boolean> = _navegarInicio
    private val _codigosCanje = MutableStateFlow<List<String>>(value = emptyList())
    val codigosCanje: StateFlow<List<String>> = _codigosCanje
    private val _navegarTienda = MutableLiveData<Boolean>()
    val navegarTienda: LiveData<Boolean> = _navegarTienda

    fun setDetallesPedido(detalles: DetallePedido) {
        _detallesPedido.value = detalles
        Log.e("COMPRA_EXITOSA", "🧾 Artículos recibidos = ${detalles.numeroArticulos}")
        val cantidad = detalles.numeroArticulos
        if (cantidad > 0) {
            inicializarCodigos(cantidad = cantidad)
        }
    }
    fun navegarAInicio() {
        _navegarInicio.value = true
    }
    fun navegarATienda() {
        _navegarTienda.value = true
    }
    fun navegacionCompletada() {
        _navegarInicio.value = false
        _navegarTienda.value = false
    }
    private fun generarCodigoSimple(): String {
        val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
        val largoBloque = 4
        val numBloque = 5
        val codigo = (1..numBloque).map {
            (1..largoBloque)
                .map { charPool[Random.nextInt(charPool.size)] }
                .joinToString("")
        }.joinToString("-")

        return codigo
    }
    fun inicializarCodigos(cantidad: Int) {
        if (cantidad > 0) {
            _codigosCanje.value = generarCodigosMultiples(cantidad)
        }
    }
    private fun generarCodigosMultiples(cantidad: Int): List<String> {
        return List(cantidad) { generarCodigoSimple() }
    }
}