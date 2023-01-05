package com.udacity.asteroid.data.workerer

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.Repository
import com.udacity.asteroidradar.data.room.SingletonDatabase
import retrofit2.HttpException

class RefreshWork(context: Context, params: WorkerParameters):CoroutineWorker(context , params) {

    private val repository = Repository(SingletonDatabase.getDatabase(context).asteroidDao)

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        return try {
            Log.d("meow" , "Getting data..")
            repository.refreshAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}