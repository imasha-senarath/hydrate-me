package com.imasha.hydrateme.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val DD_MM_YYYY = "dd-MM-yyyy"

    private fun formatDate(date: Date, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }

    fun getCurrentDate(pattern: String): String {
        val currentDate = Date()
        return formatDate(currentDate, pattern)
    }
}
