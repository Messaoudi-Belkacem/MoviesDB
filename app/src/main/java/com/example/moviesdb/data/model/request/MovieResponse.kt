package com.example.moviesdb.data.model.request

import com.example.moviesdb.data.model.Movie

data class MovieResponse(
    val results: List<Movie>
)