package com.example.moviesdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.ApiRemoteKeys

@Dao
interface ApiRemoteKeysDao {

    @Query("SELECT * FROM api_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): ApiRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ApiRemoteKeys>)

    @Query("DELETE FROM api_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}