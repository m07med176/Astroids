package com.udacity.asteroidradar.data.worker


import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.*
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.*

import java.util.concurrent.TimeUnit

class WorkScheduler : Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            setupWorker()
        }
    }

    private fun setupWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        Log.d("meow" , "In setup method")

        val refreshRequest = PeriodicWorkRequestBuilder<RefreshWork>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            Constants.REFRESH_WORKER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshRequest
        )

        val deleteRequest = PeriodicWorkRequestBuilder<DeleteWork>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            Constants.DELETE_WORKER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            deleteRequest
        )

    }
}