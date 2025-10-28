package com.example.login.model

data class Videojuego (
    val id: Int,
    val nombre: String,
    val genero: String?,
    val plataforma: String,
    val precio: Double,
    val imagen: String,
    val stock: Int
    )
