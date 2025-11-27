package com.example.login.repository

import android.content.Context
import android.util.Log
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
            Log.e("REPO_ERROR", "¡Fallo crítico en la carga del JSON!", e)
            emptyList()
        }
    }
    fun agregarVideojuego(videojuego: Videojuego) {
        println("Repositorio: Agregando el producto ${videojuego.nombre}...")
        println("Datos a guardar: ID=${videojuego.id}, Precio=${videojuego.precio}, Stock=${videojuego.stock}")
    }

    fun editarVideojuego(videojuego: Videojuego) {
        println("Repositorio: Editando el producto ${videojuego.nombre}")
    }

    fun eliminarVideojuego(videojuego: Videojuego) {
        println("Repositorio: Eliminando el producto ${videojuego.nombre} con ID ${videojuego.id}")
    }
}