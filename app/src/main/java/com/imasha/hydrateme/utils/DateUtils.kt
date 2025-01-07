package com.imasha.hydrateme.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val DD_MM_YYYY = "dd-MM-yyyy"
    const val HH_MM_AA = "hh:mm a"

    private fun formatDate(date: Date, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }

    fun getCurrentDate(pattern: String): String {
        val currentDate = Date()
        return formatDate(currentDate, pattern)
    }

    fun getCurrentTime(pattern: String): String {
        val date = Date()
        return formatDate(date, pattern)
    }
}
