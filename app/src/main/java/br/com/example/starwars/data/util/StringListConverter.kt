package br.com.example.starwars.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StringListConverter {

    private val gson: Gson = Gson()

    @TypeConverter
    fun jsonToObject(data: String): List<String> {
        if (data.isNullOrEmpty())
            return listOf()

        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun objectToJson(data: List<String>): String {
        return gson.toJson(data)
    }
}