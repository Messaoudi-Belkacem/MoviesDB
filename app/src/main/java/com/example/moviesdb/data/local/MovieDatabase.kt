package com.example.moviesdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesdb.data.local.dao.discover.ApiMovieByDiscoverDao
import com.example.moviesdb.data.local.dao.discover.ApiByDiscoverRemoteKeysDao
import com.example.moviesdb.data.local.dao.now_playing.ApiByNowPlayingRemoteKeysDao
import com.example.moviesdb.data.local.dao.now_playing.ApiMovieByNowPlayingDao
import com.example.moviesdb.data.local.dao.popular.ApiByPopularRemoteKeysDao
import com.example.moviesdb.data.local.dao.popular.ApiMovieByPopularDao
import com.example.moviesdb.data.local.dao.top_rated.ApiByTopRatedRemoteKeysDao
import com.example.moviesdb.data.local.dao.top_rated.ApiMovieByTopRatedDao
import com.example.moviesdb.data.local.dao.upcoming.ApiByUpcomingRemoteKeysDao
import com.example.moviesdb.data.local.dao.upcoming.ApiMovieByUpcomingDao
import com.example.moviesdb.data.model.ApiByDiscoverRemoteKeys
import com.example.moviesdb.data.model.ApiByNowPlayingRemoteKeys
import com.example.moviesdb.data.model.ApiByPopularRemoteKeys
import com.example.moviesdb.data.model.ApiByTopRatedRemoteKeys
import com.example.moviesdb.data.model.ApiByUpcomingRemoteKeys
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated
import com.example.moviesdb.data.model.MovieByUpcoming
import com.example.moviesdb.util.Converters

@Database(
    entities = [
        MovieByDiscover::class,
        ApiByDiscoverRemoteKeys::class,
        MovieByNowPlaying::class,
        ApiByNowPlayingRemoteKeys::class,
        MovieByPopular::class,
        ApiByPopularRemoteKeys::class,
        MovieByTopRated::class,
        ApiByTopRatedRemoteKeys::class,
        MovieByUpcoming::class,
        ApiByUpcomingRemoteKeys::class
               ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun apiMovieByDiscoverDao(): ApiMovieByDiscoverDao
    abstract fun apiMovieByNowPlayingDao(): ApiMovieByNowPlayingDao
    abstract fun apiMovieByPopularDao(): ApiMovieByPopularDao
    abstract fun apiMovieByTopRatedDao(): ApiMovieByTopRatedDao
    abstract fun apiMovieByUpcomingDao(): ApiMovieByUpcomingDao


    abstract fun apiByDiscoverRemoteKeysDao(): ApiByDiscoverRemoteKeysDao
    abstract fun apiByNowPlayingRemoteKeysDao(): ApiByNowPlayingRemoteKeysDao
    abstract fun apiByPopularRemoteKeysDao(): ApiByPopularRemoteKeysDao
    abstract fun apiByTopRatedRemoteKeysDao(): ApiByTopRatedRemoteKeysDao
    abstract fun apiByUpcomingRemoteKeysDao(): ApiByUpcomingRemoteKeysDao

}