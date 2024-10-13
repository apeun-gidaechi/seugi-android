package com.seugi.local.room.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

class LongListTypeConverter {

    @TypeConverter
    fun listLongToString(value: List<Long>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToListLong(value: String): List<Long> {
        return Gson().fromJson(value, Array<Long>::class.java).toList()
    }
}