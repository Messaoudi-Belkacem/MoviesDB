package com.example.moviesdb.data.model.response

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("request_token")val requestToken: String,
    val approved: Boolean
)
