package com.example.moviesdb.data.model.request

import com.google.gson.annotations.SerializedName

data class CreateSessionRequest(
    @SerializedName("request_token") val requestToken: String,
    val approved: Boolean,
)
