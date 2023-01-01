package com.udacity.asteroidradar.data.api

import com.udacity.asteroidradar.data.models.PictureOfDay
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceApi {

    @GET("planetary/apod")
    fun getDayPicture(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<PictureOfDay>


    @GET("neo/rest/v1/feed")
    fun getAllAsteroidsData(
        @Query("start_date") startDate:String,
        @Query("api_key") apiKey : String = Constants.API_KEY)
            : Deferred<ResponseBody>


}