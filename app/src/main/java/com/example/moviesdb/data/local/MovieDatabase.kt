package com.example.moviesdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesdb.data.local.dao.ApiMovieDao
import com.example.moviesdb.data.local.dao.ApiRemoteKeysDao
import com.example.moviesdb.data.model.ApiRemoteKeys
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.util.Converters

@Database(entities = [Movie::class, ApiRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun apiMovieDao(): ApiMovieDao
    abstract fun apiRemoteKeysDao(): ApiRemoteKeysDao

}