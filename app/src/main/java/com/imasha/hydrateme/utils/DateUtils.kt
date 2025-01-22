package com.imasha.hydrateme.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val DD_MM_YYYY = "dd-MM-yyyy"
    const val HH_MM_AA = "hh:mm a"
    const val HH_MM = "HH:mm"

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

    fun convertTo24(time12Hour: String): String {
        val inputFormat = SimpleDateFormat(HH_MM_AA, Locale.getDefault())
        val outputFormat = SimpleDateFormat(HH_MM, Locale.getDefault())
        val date = inputFormat.parse(time12Hour) ?: return ""
        return outputFormat.format(date)
    }

    fun convertTo12(time24Hour: String): String {
        val inputFormat = SimpleDateFormat(HH_MM, Locale.getDefault())
        val outputFormat = SimpleDateFormat(HH_MM_AA, Locale.getDefault())
        val date = inputFormat.parse(time24Hour) ?: return ""
        return outputFormat.format(date)
    }

    fun areTimesInCurrentPeriod(bedTime: String, wakeUpTime: String): Boolean {
        val currentTime = SimpleDateFormat(HH_MM, Locale.getDefault()).format(Date())
        val currentTimeDate = SimpleDateFormat(HH_MM, Locale.getDefault()).parse(currentTime)
        val bedTimeDate = SimpleDateFormat(HH_MM, Locale.getDefault()).parse(bedTime)
        val wakeUpTimeDate = SimpleDateFormat(HH_MM, Locale.getDefault()).parse(wakeUpTime)

        if (bedTimeDate != null) {
            if (currentTimeDate != null) {
                return if (bedTimeDate.before(wakeUpTimeDate)) {
                    !currentTimeDate.before(bedTimeDate) && currentTimeDate.before(wakeUpTimeDate)
                } else {
                    !currentTimeDate.before(bedTimeDate) || currentTimeDate.before(wakeUpTimeDate)
                }
            }
        }

        return false
    }
}
