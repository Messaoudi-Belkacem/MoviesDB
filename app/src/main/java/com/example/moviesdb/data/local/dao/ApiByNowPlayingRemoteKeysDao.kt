package com.example.moviesdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.ApiByDiscoverRemoteKeys
import com.example.moviesdb.data.model.ApiByNowPlayingRemoteKeys

@Dao
interface ApiByNowPlayingRemoteKeysDao {

    @Query("SELECT * FROM api_by_now_playing_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): ApiByNowPlayingRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ApiByNowPlayingRemoteKeys>)

    @Query("DELETE FROM api_by_now_playing_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}