package com.example.moviesdb.data.model

import com.google.gson.annotations.SerializedName



data class Review(
    val author: String,
    @SerializedName("author_details") val authorDetails: AuthorDetails,
    val content: String,
    @SerializedName("created_at") val createdAt: String,
    val id: String,
    @SerializedName("updated_at") val updatedAt: String,
    val url: String
)

data class AuthorDetails(
    val name: String,
    val username: String,
    @SerializedName("avatar_path") val avatarPath: String?,
    val rating: Int?
)