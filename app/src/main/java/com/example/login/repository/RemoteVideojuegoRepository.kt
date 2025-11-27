package com.example.login.repository

import com.example.login.model.Videojuego
import com.example.login.network.ApiService

class RemoteVideojuegoRepository(private val api: ApiService) {
    suspend fun obtenerVideojuegosRawg(apiKey: String, pageSize: Int = 20): List<Videojuego> {
        val resp = api.getGames(apiKey, pageSize)
        return resp.toVideojuegos()
    }
}
