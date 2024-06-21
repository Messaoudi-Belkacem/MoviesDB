package com.example.moviesdb.data.local.dao.popular

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.ApiByPopularRemoteKeys

@Dao
interface ApiByPopularRemoteKeysDao {

    @Query("SELECT * FROM api_by_popular_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): ApiByPopularRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ApiByPopularRemoteKeys>)

    @Query("DELETE FROM api_by_popular_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}