package com.example.moviesdb.data.model.response

import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated
import com.example.moviesdb.data.model.MovieByUpcoming

data class MovieResponse(
    val results: List<Movie>
)

data class MovieResponseByDiscover(
    val results: List<MovieByDiscover>
)

data class MovieResponseByNowPlaying(
    val results: List<MovieByNowPlaying>
)

data class MovieResponseByPopular(
    val results: List<MovieByPopular>
)

data class MovieResponseByTopRated(
    val results: List<MovieByTopRated>
)

data class MovieResponseByUpcoming(
    val results: List<MovieByUpcoming>
)