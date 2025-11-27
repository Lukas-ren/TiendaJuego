package com.example.login.repository

import com.example.login.model.Videojuego
import com.example.login.network.ApiService

class RemoteVideojuegoRepository(private val api: ApiService) {
    suspend fun obtenerVideojuegos(): List<Videojuego> = api.obtenerVideojuegos()
}
