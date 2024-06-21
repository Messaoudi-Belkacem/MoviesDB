package com.example.moviesdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdb.util.Constants.Companion.API_MOVIE_BY_DISCOVER_TABLE
import com.example.moviesdb.util.Constants.Companion.API_MOVIE_BY_NOW_PLAYING_TABLE
import com.example.moviesdb.util.Constants.Companion.API_MOVIE_BY_POPULAR_TABLE
import com.example.moviesdb.util.Constants.Companion.API_MOVIE_BY_TOP_RATED_TABLE
import com.example.moviesdb.util.Constants.Companion.API_MOVIE_BY_UPCOMING_TABLE
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val genreIds: List<Int>,
    val voteAverage: Double,
    val voteCount: Int
)

@Entity(tableName = API_MOVIE_BY_DISCOVER_TABLE)
data class MovieByDiscover(
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

@Entity(tableName = API_MOVIE_BY_NOW_PLAYING_TABLE)
data class MovieByNowPlaying(
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

@Entity(tableName = API_MOVIE_BY_POPULAR_TABLE)
data class MovieByPopular(
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

@Entity(tableName = API_MOVIE_BY_TOP_RATED_TABLE)
data class MovieByTopRated(
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

@Entity(tableName = API_MOVIE_BY_UPCOMING_TABLE)
data class MovieByUpcoming(
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