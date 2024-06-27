package com.example.moviesdb.data.model.request

import com.google.gson.annotations.SerializedName

data class AddToWatchlistRequest(
    @SerializedName("media_type") val mediaType: String = "movie",
    @SerializedName("media_id") val mediaID: Int,
    val watchlist: Boolean = true
)