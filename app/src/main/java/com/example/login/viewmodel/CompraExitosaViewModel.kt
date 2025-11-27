package com.example.login.viewmodel

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
    private val _detallesPedido = MutableLiveData<DetallePedido>()
    val detallesPedido: LiveData<DetallePedido> = _detallesPedido
    private val _navegarInicio = MutableLiveData<Boolean>()
    val navegarInicio: LiveData<Boolean> = _navegarInicio
    private val _codigoCanje = MutableStateFlow("")
    val codigoCanje: StateFlow<String> = _codigoCanje

    private val _navegarTienda = MutableLiveData<Boolean>()
    val navegarTienda: LiveData<Boolean> = _navegarTienda
    init {
        _codigoCanje.value = generarCodigo()
    }
    fun setDetallesPedido(detalles: DetallePedido) {
        _detallesPedido.value = detalles
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
    private fun generarCodigo(): String { // Lo he puesto privado porque solo se usa internamente
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
}