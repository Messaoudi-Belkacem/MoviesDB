package com.example.moviesdb.data.model.response

import com.google.gson.annotations.SerializedName

data class CreateSessionResponse(
    val success: Boolean,
    @SerializedName("session_id") val sessionID: String
)
