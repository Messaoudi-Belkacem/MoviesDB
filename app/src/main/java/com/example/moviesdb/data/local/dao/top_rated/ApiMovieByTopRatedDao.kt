package com.example.moviesdb.data.local.dao.top_rated

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated

@Dao
interface ApiMovieByTopRatedDao {

    @Query("SELECT * FROM api_movie_by_top_rated_table")
    fun getAllMovies(): PagingSource<Int, MovieByTopRated>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieByTopRated>)

    @Query("DELETE FROM api_movie_by_top_rated_table")
    suspend fun deleteAllMovies()

}