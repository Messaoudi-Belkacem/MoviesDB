package com.example.moviesdb.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.Movie

@Dao
interface ApiMovieDao {

    @Query("SELECT * FROM api_movie_table")
    fun getAllMovies(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Query("DELETE FROM api_movie_table")
    suspend fun deleteAllMovies()

}