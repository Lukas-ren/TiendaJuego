package com.example.login.model

import com.example.login.model.Videojuego
import com.example.login.model.RawgGamesResponse

fun GameModel.toVideojuego(): Videojuego {
    return Videojuego(
        id = this.id,
        nombre = this.name,
        imagen = this.backgroundImage ?: "",
        genero = null,
        plataforma = "Desconocida",
        precio = 0,
        stock = 0
    )
}


fun RawgGamesResponse.toVideojuegos(): List<Videojuego> {
    // Mapea la lista 'results' de la respuesta a una lista de Videojuego
    return this.results.map { gameModel ->
        gameModel.toVideojuego()
    }
}