package com.imasha.hydrateme.utils

import com.imasha.hydrateme.utils.AppConstants.BED_TIME
import com.imasha.hydrateme.utils.AppConstants.IS_INIT_REMINDER
import com.imasha.hydrateme.utils.AppConstants.IS_NOTIFICATION_ON
import com.imasha.hydrateme.utils.AppConstants.WAKE_UP_TIME
import com.imasha.hydrateme.utils.DateUtils.areTimesInCurrentPeriod
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefBoolean
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefString

object Validations {

    fun Any.isAnyNotEmpty(): Boolean {
        return when (this) {
            is String -> this.isNotEmpty()
            is Double -> this != 0.0
            else -> false
        }
    }

    fun shouldSendNotification(): Boolean {
        if (!getPrefBoolean(IS_INIT_REMINDER)) {
            return false
        }

        if (areTimesInCurrentPeriod(getPrefString(BED_TIME), getPrefString(WAKE_UP_TIME))) {
            return false
        }

        if (!getPrefBoolean(IS_NOTIFICATION_ON, true)) {
            return false
        }

        return true
    }
}