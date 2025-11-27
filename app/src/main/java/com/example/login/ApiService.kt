package com.example.login.network

import com.example.login.model.Videojuego
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 20
    ): RawgGamesResponse
}
