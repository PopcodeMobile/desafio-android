package com.example.desafio_android.data.db

import androidx.room.TypeConverter
import com.example.desafio_android.data.model.People
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverts {

    var gson = Gson()

    @TypeConverter
    fun peopleToString(people: People): String {
        return gson.toJson(people)
    }

    @TypeConverter
    fun stringToPeople(data: String): People {
        val listType = object : TypeToken<People>() {}.type
        return gson.fromJson(data, listType)
    }

}