package com.example.moviesdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdb.util.Constants.Companion.API_BY_DISCOVER_REMOTE_KEYS_TABLE
import com.example.moviesdb.util.Constants.Companion.API_BY_NOW_PLAYING_REMOTE_KEYS_TABLE

data class ApiRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val prevPage: Int?,
    val nextPage: Int?
)

@Entity(tableName = API_BY_DISCOVER_REMOTE_KEYS_TABLE)
data class ApiByDiscoverRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val prevPage: Int?,
    val nextPage: Int?
)

@Entity(tableName = API_BY_NOW_PLAYING_REMOTE_KEYS_TABLE)
data class ApiByNowPlayingRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val prevPage: Int?,
    val nextPage: Int?
)