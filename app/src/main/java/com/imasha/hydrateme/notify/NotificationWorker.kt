package com.imasha.hydrateme.notify

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.imasha.hydrateme.notify.NotificationUtils.showNotification
import com.imasha.hydrateme.utils.AppConstants.BED_TIME
import com.imasha.hydrateme.utils.AppConstants.IS_INIT_REMINDER
import com.imasha.hydrateme.utils.AppConstants.IS_NOTIFICATION_ON
import com.imasha.hydrateme.utils.AppConstants.WAKE_UP_TIME
import com.imasha.hydrateme.utils.DateUtils.areTimesInCurrentPeriod
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefBoolean
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefString
import com.imasha.hydrateme.utils.Validations.shouldSendNotification

class NotificationWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        if(!shouldSendNotification()) {
            return Result.success()
        }

        showNotification(
            applicationContext, "Time to Hydrate", "It's time to refresh! Grab a glass of water."
        )

        return Result.success()
    }
}
