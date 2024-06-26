package com.example.moviesdb.data.model.request

import com.google.gson.annotations.SerializedName

data class DeleteSessionRequest(
    @SerializedName("session_id") val sessionID: String
)