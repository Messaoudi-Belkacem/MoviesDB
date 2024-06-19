package com.example.moviesdb.data.remote

import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.request.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun getAllMoviesByDiscover(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getAllMoviesByNowPlaying(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieResponse

}