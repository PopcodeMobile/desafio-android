package com.example.starwarswiki.util

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun stringListToString(stringList: List<String>): String{
        return if(stringList.size > 1)
            stringList.joinToString()
        else
            stringList[0]
    }

    @TypeConverter
    fun stringToStringList(string: String): List<String>{
         return string.split(",")
    }
}