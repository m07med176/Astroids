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

    // region Asteroid Items
    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids:LiveData<List<Asteroid>>
        get() = _asteroids

    private val _allAsteroidsItems = MutableLiveData<List<Asteroid>>()
    val allAsteroidsItems:LiveData<List<Asteroid>>
        get() = _allAsteroidsItems
    // endregion Asteroid Items

    // region Picture
    private val _picture = MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay>
        get() = _picture

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getAllAsteroids() = viewModelScope.launch(Dispatchers.IO){
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
            _allAsteroidsItems.postValue(it)
        }
        _loading.postValue(false)
    }

    private fun getCurrentPic() = viewModelScope.launch(Dispatchers.IO){
        val picture = repository.getDayPicture()
        picture?.let {it->
            _picture.postValue(it)
        }
    }
    // endregion Picture

    init {
        viewModelScope.launch (Dispatchers.Default){
            _loading.postValue(true)
            withContext(Dispatchers.IO) {
                getCurrentPic()
            }
            viewModelScope.launch{
                getAllAsteroids()
            }
        }
    }

    // region Loading
    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
        get() = _loading
    // endregion Loading

    // region Navigation
    private val _navigateToFragmentDetails:MutableLiveData<Asteroid>? = MutableLiveData<Asteroid>()
    val fragmentDetailsNavigate
        get() = _navigateToFragmentDetails

    fun onItemClicked(asteroid: Asteroid){
        _navigateToFragmentDetails!!.value = asteroid
    }

    fun navigateToFragmentDetails() {
        _navigateToFragmentDetails!!.value = null
    }
    // endregion Navigation

    fun getToday():String = Utils.getToday()
}