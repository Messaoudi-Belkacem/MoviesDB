package com.example.moviesdb.data.model.request

import com.example.moviesdb.data.model.CastMember
import com.example.moviesdb.data.model.Review

data class CastResponse(
    val cast: List<CastMember>
)