package com.example.moviesdb.data.local.dao.discover

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.ApiByDiscoverRemoteKeys

@Dao
interface ApiByDiscoverRemoteKeysDao {

    @Query("SELECT * FROM api_by_discover_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): ApiByDiscoverRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ApiByDiscoverRemoteKeys>)

    @Query("DELETE FROM api_by_discover_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}