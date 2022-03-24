package com.example.leotvshowapp.data.source.local

import androidx.room.TypeConverter
import com.example.leotvshowapp.data.dto.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromScheduleType(value: Schedule?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToScheduleType(schedule: String?): Schedule? {
        val scheduleType = object : TypeToken<Schedule?>() {}.type
        return Gson().fromJson(schedule, scheduleType)
    }
}