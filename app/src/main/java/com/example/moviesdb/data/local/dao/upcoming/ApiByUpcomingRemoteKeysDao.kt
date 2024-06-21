package com.example.moviesdb.data.local.dao.upcoming

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.ApiByDiscoverRemoteKeys
import com.example.moviesdb.data.model.ApiByUpcomingRemoteKeys

@Dao
interface ApiByUpcomingRemoteKeysDao {

    @Query("SELECT * FROM api_by_upcoming_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): ApiByUpcomingRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ApiByDiscoverRemoteKeys>)

    @Query("DELETE FROM api_by_upcoming_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}