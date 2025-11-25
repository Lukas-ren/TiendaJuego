package com.example.login.repository

import android.content.Context
import com.example.login.model.Videojuego
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VideojuegoRepository {

    fun obtenerVideojuego(context: Context, filename: String = "videojuego.json"): List<Videojuego> {
        return try {
            val json = context.assets.open(filename).bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<Videojuego>>() {}.type
            Gson().fromJson<List<Videojuego>>(json, listType) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    fun editarVideojuego(videojuego: Videojuego) {
        println("Repositorio: Editando el producto ${videojuego.nombre}")
    }
    fun eliminarVideojuego(videojuego: Videojuego) {
        println("Repositorio: Eliminando el producto ${videojuego.nombre}")
    }
}