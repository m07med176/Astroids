package com.udacity.asteroidradar.data.room

import androidx.room.*
import com.udacity.asteroidradar.data.models.Asteroid

@Dao
interface AsteroidDAO {

    // Retreive
    @Query("SELECT * FROM Asteroid WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsByCloseApproachDateRange(startDate: String, endDate: String): List<Asteroid>

    @Query("SELECT * FROM Asteroid ORDER BY closeApproachDate ASC")
    fun getAllAsteroidsData(): List<Asteroid>

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg asteroid: Asteroid)

    // Delete
    @Query("DELETE FROM Asteroid WHERE closeApproachDate < :date")
    fun deleteByDate(date: String): Int

}