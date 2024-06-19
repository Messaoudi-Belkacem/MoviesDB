package com.example.moviesdb.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromGenreIds(genreIds: List<Int>?): String? {
        return if (genreIds == null) null else Gson().toJson(genreIds)
    }

    @TypeConverter
    fun toGenreIds(genreIdsString: String?): List<Int>? {
        return if (genreIdsString == null) null else Gson().fromJson(genreIdsString, object : TypeToken<List<Int>>() {}.type)
    }
}
