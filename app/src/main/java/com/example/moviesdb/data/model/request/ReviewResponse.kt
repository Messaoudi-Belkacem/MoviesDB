package com.example.moviesdb.data.model.request

import com.example.moviesdb.data.model.MovieByUpcoming
import com.example.moviesdb.data.model.Review

data class ReviewResponse(
    val results: List<Review>
)