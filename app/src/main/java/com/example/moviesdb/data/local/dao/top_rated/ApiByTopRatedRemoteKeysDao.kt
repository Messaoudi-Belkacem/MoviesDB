package com.example.moviesdb.data.local.dao.top_rated

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.ApiByTopRatedRemoteKeys

@Dao
interface ApiByTopRatedRemoteKeysDao {

    @Query("SELECT * FROM api_by_top_rated_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): ApiByTopRatedRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ApiByTopRatedRemoteKeys>)

    @Query("DELETE FROM api_by_top_rated_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}