package com.udacity.asteroidradar.data.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.Repository
import com.udacity.asteroidradar.data.room.SingletonDatabase
import retrofit2.HttpException

class DeleteWork (context: Context, params: WorkerParameters)
    : CoroutineWorker(context , params) {

    private val repository =
        Repository(SingletonDatabase.getDatabase(context).asteroidDao)

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        return try {
            repository.deleteOutDatedAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}