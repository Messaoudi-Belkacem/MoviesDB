package com.example.moviesdb.data.local.dao.popular

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByPopular

@Dao
interface ApiMovieByPopularDao {

    @Query("SELECT * FROM api_movie_by_popular_table")
    fun getAllMovies(): PagingSource<Int, MovieByPopular>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieByPopular>)

    @Query("DELETE FROM api_movie_by_popular_table")
    suspend fun deleteAllMovies()

}