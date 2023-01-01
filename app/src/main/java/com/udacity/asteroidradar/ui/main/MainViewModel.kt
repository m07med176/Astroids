package com.udacity.asteroid.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.Repository
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.data.models.PictureOfDay
import com.udacity.asteroidradar.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids:LiveData<List<Asteroid>>
        get() = _asteroids

    private val _allAsteroids = MutableLiveData<List<Asteroid>>()
    val allAsteroids:LiveData<List<Asteroid>>
        get() = _allAsteroids

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay>
        get() = _picture

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getAsteroids() = viewModelScope.launch(Dispatchers.IO){
        var asteroids = repository.getAllAsteroidsData()
        var allAsteroids = repository.getAllAsteroidsData()
        if (asteroids.isEmpty()){
            repository.refreshAsteroids()
            asteroids = repository.getAllAsteroidsData()
            allAsteroids =repository.getAllAsteroidsData()
        }
        asteroids?.let {
            _asteroids.postValue(it)
        }
        allAsteroids?.let {
            _allAsteroids.postValue(it)
        }
        _loading.postValue(false)
    }



    private fun getTodayPicture() = viewModelScope.launch(Dispatchers.IO){
        val picture = repository.getDayPicture()
        picture?.let {it->
        _picture.postValue(it)
        }
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
        get() = _loading

    init {
        viewModelScope.launch (Dispatchers.Default){
            _loading.postValue(true)
            withContext(Dispatchers.IO) {
                getTodayPicture()
            }
            viewModelScope.launch{
                getAsteroids()
            }
        }
    }

    private val _navigateToAsteroidDetail:MutableLiveData<Asteroid>? = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToAsteroidDetail!!.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail!!.value = null
    }

    fun getToday():String = Utils.getToday()
}