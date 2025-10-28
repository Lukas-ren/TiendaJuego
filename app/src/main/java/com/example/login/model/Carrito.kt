package com.example.login.model

data class Carrito (
    val id : Int,
    val nombre: String,
    val precio: Double,
    var cantidad: Int = 1,
    val imagen: String = ""
) {
    val subtotal: Double
        get() = precio * cantidad
}
