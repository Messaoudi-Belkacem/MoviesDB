package com.example.moviesdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdb.util.Constants.Companion.API_MOVIE_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = API_MOVIE_TABLE)
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)