package com.example.login.model

data class RawgGamesResponse(
    val results: List<RawgGameDto> = emptyList()
)

data class RawgGameDto(
    val id: Int,
    val name: String,
    val background_image: String?,
    val genres: List<RawgGenreDto>?,
    val platforms: List<RawgPlatformWrapper>?
)

data class RawgGenreDto(val name: String?)
data class RawgPlatformWrapper(val platform: RawgPlatformDto?)
data class RawgPlatformDto(val name: String?)

fun RawgGamesResponse.toVideojuegos(): List<Videojuego> = results.map { game ->
    Videojuego(
        id = game.id,
        nombre = game.name,
        genero = game.genres?.firstOrNull()?.name ?: "N/A",
        plataforma = game.platforms?.firstOrNull()?.platform?.name ?: "Unknown",
        precio = 0,
        imagen = game.background_image ?: "",
        stock = 10
    )
}
