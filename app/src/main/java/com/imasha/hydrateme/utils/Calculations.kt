package com.imasha.hydrateme.utils

import com.imasha.hydrateme.data.model.Record

object Calculations {

    fun getWaterIntake(weight: Double, isMale: Boolean, exerciseMinutes: Double): Int {
        var baseWaterLiters = 3.2

        if (weight > 0) {
            baseWaterLiters = weight * 0.033

            if (isMale) {
                baseWaterLiters += 0.5
            }

            baseWaterLiters += exerciseMinutes / 30 * 0.35
        }

        return (baseWaterLiters * 1000).toInt()
    }

    fun getTotalWaterUsage(items: List<Record>): Int {
        return items.sumOf { it.size }
    }

    fun getTotalByDate(records: List<Record>): List<Record> {
        return records
            .groupBy { it.date }
            .map { (date, recordsForDate) ->
                Record(
                    id = "",
                    user = "",
                    size = recordsForDate.sumOf { it.size },
                    time = "",
                    date = date
                )
            }
    }
}