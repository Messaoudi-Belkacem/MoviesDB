package com.example.moviesdb.data.local.dao.discover

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.MovieByDiscover

@Dao
interface ApiMovieByDiscoverDao {

    @Query("SELECT * FROM api_movie_by_discover_table")
    fun getAllMovies(): PagingSource<Int, MovieByDiscover>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieByDiscover>)

    @Query("DELETE FROM api_movie_by_discover_table")
    suspend fun deleteAllMovies()

}