package com.example.moviesdb.data.model.request

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("request_token")val requestToken: String,
    val approved: Boolean
)
