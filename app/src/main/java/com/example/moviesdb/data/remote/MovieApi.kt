package com.example.moviesdb.data.remote

import com.example.moviesdb.data.model.User
import com.example.moviesdb.data.model.request.AddToWatchlistRequest
import com.example.moviesdb.data.model.response.CastResponse
import com.example.moviesdb.data.model.request.CreateSessionRequest
import com.example.moviesdb.data.model.response.AddToWatchlistResponse
import com.example.moviesdb.data.model.response.CreateSessionResponse
import com.example.moviesdb.data.model.response.MovieResponse
import com.example.moviesdb.data.model.response.MovieResponseByDiscover
import com.example.moviesdb.data.model.response.MovieResponseByNowPlaying
import com.example.moviesdb.data.model.response.MovieResponseByPopular
import com.example.moviesdb.data.model.response.MovieResponseByTopRated
import com.example.moviesdb.data.model.response.MovieResponseByUpcoming
import com.example.moviesdb.data.model.response.RequestTokenResponse
import com.example.moviesdb.data.model.response.ReviewResponse
import com.example.moviesdb.data.model.response.DeleteSessionResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @DELETE("authentication/session")
    suspend fun deleteSession(
        @Query("session_id") sessionID: String
    ): DeleteSessionResponse

    @GET("account/{account_id}")
    suspend fun getAccountDetails(
        @Path("account_id") accountID: Int = 20139247,
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

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountID: Int,
        @Query("session_id") sessionID: String,
        @Body rawBody: AddToWatchlistRequest
    ): AddToWatchlistResponse
}