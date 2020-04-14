package com.edenh.newsclient.repository

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): String {
        return buildFormat().format(Date(value))
    }

    @TypeConverter
    fun dateToTimestamp(date: String): Long? {
        return buildFormat().parse(date)?.time
    }

    private fun buildFormat(): SimpleDateFormat {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT")
        return format
    }
}
