package com.udacity.asteroidradar.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import com.udacity.asteroidradar.utils.Constants
object RetrofitSingltone {
    private val client = OkHttpClient()
        .newBuilder()
        .readTimeout(60 , TimeUnit.SECONDS)
        .connectTimeout(60 , TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()

    val networkInterface: WebServiceApi = retrofit.create(WebServiceApi::class.java)

}