package com.example.moviesdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesdb.data.local.dao.discover.ApiMovieByDiscoverDao
import com.example.moviesdb.data.local.dao.discover.ApiByDiscoverRemoteKeysDao
import com.example.moviesdb.data.local.dao.ApiByNowPlayingRemoteKeysDao
import com.example.moviesdb.data.local.dao.ApiMovieByNowPlayingDao
import com.example.moviesdb.data.model.ApiByDiscoverRemoteKeys
import com.example.moviesdb.data.model.ApiByNowPlayingRemoteKeys
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.util.Converters

@Database(entities = [MovieByDiscover::class, ApiByDiscoverRemoteKeys::class, MovieByNowPlaying::class, ApiByNowPlayingRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun apiMovieByDiscoverDao(): ApiMovieByDiscoverDao
    abstract fun apiMovieByNowPlayingDao(): ApiMovieByNowPlayingDao
    abstract fun apiByDiscoverRemoteKeysDao(): ApiByDiscoverRemoteKeysDao
    abstract fun apiByNowPlayingRemoteKeysDao(): ApiByNowPlayingRemoteKeysDao

}