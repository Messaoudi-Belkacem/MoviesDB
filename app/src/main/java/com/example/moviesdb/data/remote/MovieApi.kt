package com.example.moviesdb.data.remote

import com.example.moviesdb.data.model.User
import com.example.moviesdb.data.model.request.CastResponse
import com.example.moviesdb.data.model.request.CreateSessionRequest
import com.example.moviesdb.data.model.request.CreateSessionResponse
import com.example.moviesdb.data.model.request.MovieResponse
import com.example.moviesdb.data.model.request.MovieResponseByDiscover
import com.example.moviesdb.data.model.request.MovieResponseByNowPlaying
import com.example.moviesdb.data.model.request.MovieResponseByPopular
import com.example.moviesdb.data.model.request.MovieResponseByTopRated
import com.example.moviesdb.data.model.request.MovieResponseByUpcoming
import com.example.moviesdb.data.model.request.RequestTokenResponse
import com.example.moviesdb.data.model.request.ReviewResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieID: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): ReviewResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieID: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): CastResponse

    // Authentication
    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(
        @Body rawBody: CreateSessionRequest
    ): CreateSessionResponse

    @GET("account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionID: String,
    ): User

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchListMovies(
        @Path("account_id") accountID: Int,
        @Query("session_id") sessionID: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "created_at.asc",
    ): MovieResponse

}