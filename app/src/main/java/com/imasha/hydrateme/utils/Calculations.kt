package com.imasha.hydrateme.utils

object Calculations {

    fun waterIntake(weight: Double, isMale: Boolean, exerciseMinutes: Double): Int {
        var baseWater = weight * 0.033
        if (isMale) {
            baseWater += 0.5
        }
        baseWater += exerciseMinutes / 30 * 0.35
        return baseWater.toInt()
    }
}