package com.seugi.local.room.util

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson

class LongListTypeConverter {

    @TypeConverter
    fun listLongToString(value: List<Long>): String {
        val json = Gson().toJson(value)
        Log.d("LongListTypeConverter", "Converted List<Long> to JSON: $json")
        return json
    }

    @TypeConverter
    fun stringToListLong(value: String): List<Long> {
        val list = Gson().fromJson(value, Array<Long>::class.java).toList()
        Log.d("LongListTypeConverter", "Converted JSON to List<Long>: $list")
        return list
    }
}
