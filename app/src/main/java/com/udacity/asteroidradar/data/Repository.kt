package com.udacity.asteroidradar.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.data.api.RetrofitSingltone
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.data.models.PictureOfDay
import com.udacity.asteroidradar.data.room.AsteroidDAO
import com.udacity.asteroidradar.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.ArrayList

class Repository(private val db: AsteroidDAO){

    // Network
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshAsteroids(): ArrayList<Asteroid> {
        var asteroids: ArrayList<Asteroid>
        withContext(Dispatchers.IO){
            val response = RetrofitSingltone.networkInterface.getAllAsteroidsData(Utils.getToday()).await()
            asteroids = parseAsteroidsJsonResult(JSONObject(response.string()))
        }
        return asteroids
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteOutDatedAsteroids(){
        db.deleteByDate(Utils.getToday())
    }

    suspend fun getDayPicture() : PictureOfDay {
        return RetrofitSingltone.networkInterface.getDayPicture().await()
    }


    // Room
    fun getAllAsteroidsData (): List<Asteroid> = db.getAllAsteroidsData()

    fun getAsteroidsByCloseApproachDateRange() : List<Asteroid> =  db.getAsteroidsByCloseApproachDateRange(Utils.getToday(),Utils.getNextWeak())

    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteByDate(){
        db.deleteByDate(Utils.getToday())
    }


}