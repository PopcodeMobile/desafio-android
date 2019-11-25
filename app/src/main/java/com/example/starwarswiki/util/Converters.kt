package com.example.starwarswiki.util

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun stringListToString(stringList: List<String>): String{
        return if(stringList.isEmpty())
            ""
        else
            stringList.joinToString()

    }

    @TypeConverter
    fun stringToStringList(string: String): List<String>{
         return string.split(",")
    }
}