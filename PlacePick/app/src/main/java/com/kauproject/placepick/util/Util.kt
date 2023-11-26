package com.kauproject.placepick.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Util {
    fun convertTimeToDateString(timeInMillis: Long): String {
        val date = Date(timeInMillis)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }
}