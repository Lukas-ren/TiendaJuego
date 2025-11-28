package com.example.login.model

import com.google.gson.annotations.SerializedName

data class GameModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("background_image") // 👈 Este es el campo clave para las imágenes
    val backgroundImage: String?,

)