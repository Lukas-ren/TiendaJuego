package com.example.login.model

data class DetallePedido(
    val idPedido: String,
    val totalCompra: Int,
    val numeroArticulos: Int,
    val metodoPago: String
)