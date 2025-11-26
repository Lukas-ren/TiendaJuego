package com.example.login.network

import com.example.login.model.Videojuego
import retrofit2.http.GET

interface ApiService {
    
    @GET("videojuegos")
    suspend fun obtenerVideojuegos(): List<Videojuego>
}
