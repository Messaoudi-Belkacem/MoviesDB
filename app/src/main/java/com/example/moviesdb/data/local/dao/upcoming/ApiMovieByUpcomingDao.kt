package com.example.moviesdb.data.local.dao.upcoming

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated
import com.example.moviesdb.data.model.MovieByUpcoming

@Dao
interface ApiMovieByUpcomingDao {

    @Query("SELECT * FROM api_movie_by_upcoming_table")
    fun getAllMovies(): PagingSource<Int, MovieByUpcoming>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieByUpcoming>)

    @Query("DELETE FROM api_movie_by_upcoming_table")
    suspend fun deleteAllMovies()

}