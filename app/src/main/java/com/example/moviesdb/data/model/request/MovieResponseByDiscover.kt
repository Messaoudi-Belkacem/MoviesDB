package com.example.moviesdb.data.model.request

import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying

data class MovieResponseByDiscover(
    val results: List<MovieByDiscover>
)

data class MovieResponseByNowPlaying(
    val results: List<MovieByNowPlaying>
)