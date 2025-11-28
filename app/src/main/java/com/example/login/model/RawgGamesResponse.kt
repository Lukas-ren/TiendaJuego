package com.example.login.model

import com.google.gson.annotations.SerializedName

data class RawgGamesResponse (
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<GameModel>
)