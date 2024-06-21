package com.example.moviesdb.data.remote

import com.example.moviesdb.data.model.request.MovieResponseByDiscover
import com.example.moviesdb.data.model.request.MovieResponseByNowPlaying
import com.example.moviesdb.data.model.request.MovieResponseByPopular
import com.example.moviesdb.data.model.request.MovieResponseByTopRated
import com.example.moviesdb.data.model.request.MovieResponseByUpcoming
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun getAllMoviesByDiscover(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): MovieResponseByDiscover

    @GET("movie/now_playing")
    suspend fun getAllMoviesByNowPlaying(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponseByNowPlaying

    @GET("movie/popular")
    suspend fun getAllMoviesByPopular(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponseByPopular

    @GET("movie/top_rated")
    suspend fun getAllMoviesByTopRated(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponseByTopRated

    @GET("movie/upcoming")
    suspend fun getAllMoviesByUpcoming(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponseByUpcoming

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieResponseByDiscover

}