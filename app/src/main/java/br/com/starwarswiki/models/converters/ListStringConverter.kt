package br.com.starwarswiki.models.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.converter.PropertyConverter

class ListStringConverter : PropertyConverter<List<String>, String> {
    override fun convertToDatabaseValue(entityProperty: List<String>?): String {
        if (entityProperty == null) return ""

        return Gson().toJson(entityProperty)
    }

    override fun convertToEntityProperty(databaseValue: String?): List<String> {
        if (databaseValue == null) return emptyList()
        val listType = object : TypeToken<List<String>>() { }.type
        return Gson().fromJson(databaseValue, listType)
    }

}