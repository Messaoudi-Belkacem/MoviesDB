package com.example.moviesdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdb.util.Constants.Companion.API_REMOTE_KEYS_TABLE

@Entity(tableName = API_REMOTE_KEYS_TABLE)
data class ApiRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val prevPage: Int?,
    val nextPage: Int?
)