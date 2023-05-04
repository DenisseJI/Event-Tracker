package com.example.a2project.database

import androidx.room.TypeConverter
import java.util.*

class EventTypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID): String? {
        return uuid.toString()
    }
    @TypeConverter
    fun uuidFromString(string: String?): UUID? {
        return UUID.fromString(string)
    }
}