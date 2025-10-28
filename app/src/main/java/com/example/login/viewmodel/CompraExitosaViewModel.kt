package com.example.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login.model.DetallePedido

class CompraExitosaViewModel : ViewModel() {
    private val _detallesPedido = MutableLiveData<DetallePedido>()
    val detallesPedido: LiveData<DetallePedido> = _detallesPedido
    private val _navegarInicio = MutableLiveData<Boolean>()
    val navegarInicio: LiveData<Boolean> = _navegarInicio

    private val _navegarTienda = MutableLiveData<Boolean>()
    val navegarTienda: LiveData<Boolean> = _navegarTienda
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
}