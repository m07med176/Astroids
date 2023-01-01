package com.udacity.asteroidradar.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.data.api.RetrofitSingltone
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.data.models.PictureOfDay
import com.udacity.asteroidradar.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.ArrayList

class Repository {

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshAsteroids(): ArrayList<Asteroid> {
        var asteroids: ArrayList<Asteroid>
        withContext(Dispatchers.IO){
            val response = RetrofitSingltone.networkInterface.getAllAsteroidsData(Utils.getToday()).await()
            asteroids = parseAsteroidsJsonResult(JSONObject(response.string()))
        }
        return asteroids
    }

    suspend fun getDayPicture() : PictureOfDay {
        return RetrofitSingltone.networkInterface.getDayPicture().await()
    }
}