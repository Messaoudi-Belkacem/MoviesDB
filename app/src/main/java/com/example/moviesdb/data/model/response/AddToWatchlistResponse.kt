package com.example.moviesdb.data.model.response

import com.google.gson.annotations.SerializedName

data class AddToWatchlistResponse(
    val success: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
)