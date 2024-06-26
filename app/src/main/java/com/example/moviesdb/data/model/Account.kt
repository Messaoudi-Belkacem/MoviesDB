package com.example.moviesdb.data.model

import com.google.gson.annotations.SerializedName

data class Avatar(
    val gravatar: Gravatar
)

data class Gravatar(
    val hash: String
)

data class User(
    val avatar: Avatar,
    val id: Int,
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("iso_3166_1") val region: String,
    val name: String,
    @SerializedName("include_adult") val includeAdult: Boolean,
    val username: String
)
