package com.vilo.muradyan.task.data.database

import androidx.room.TypeConverter
import com.vilo.muradyan.task.data.models.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    var gson:Gson = Gson()
    @TypeConverter
    fun fromStringtoTag( data: String?) : List<Tag>? {
        if( data == null ) {
            return null
        }

        val obj = object : TypeToken<List<Tag>>(){}.type

        return gson.fromJson(data, obj)
    }

    @TypeConverter
    fun fromListTagToString( someObjects : List<Tag>?) : String? {
        if(someObjects == null) {
            return null
        }
        return gson.toJson(someObjects)
    }
}