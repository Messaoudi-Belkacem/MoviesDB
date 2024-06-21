package com.example.moviesdb.data.local.dao.now_playing

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.MovieByNowPlaying

@Dao
interface ApiMovieByNowPlayingDao {

    @Query("SELECT * FROM api_movie_by_now_playing_table")
    fun getAllMovies(): PagingSource<Int, MovieByNowPlaying>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieByNowPlaying>)

    @Query("DELETE FROM api_movie_by_now_playing_table")
    suspend fun deleteAllMovies()

}