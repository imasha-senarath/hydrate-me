package com.imasha.hydrateme.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val DD_MM_YYYY = "dd-MM-yyyy"

    fun getCurrentDate(pattern: String): String {
        val currentDate = Date()
        return formatDate(currentDate, pattern)
    }

    // Format a Date to a string with the given pattern
    fun formatDate(date: Date, pattern: String): String {

        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }

    // Parse a string to a Date object with the given pattern
    fun parseDate(dateString: String, pattern: String): Date? {
        return try {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            formatter.parse(dateString)
        } catch (e: Exception) {
            null // Return null if parsing fails
        }
    }

    // Add or subtract days from a Date
    fun addDaysToDate(date: Date, days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar.time
    }

    // Check if two dates are on the same day
    fun isSameDay(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = date1 }
        val calendar2 = Calendar.getInstance().apply { time = date2 }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }

    // Get the difference in days between two dates
    fun getDaysBetweenDates(startDate: Date, endDate: Date): Long {
        val differenceInMillis = endDate.time - startDate.time
        return differenceInMillis / (1000 * 60 * 60 * 24)
    }

    // Get a Date object representing the start of the day
    fun getStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    // Get a Date object representing the end of the day
    fun getEndOfDay(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.time
    }
}
