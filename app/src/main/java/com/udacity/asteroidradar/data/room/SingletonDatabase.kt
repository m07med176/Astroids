package com.udacity.asteroidradar.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.data.models.Asteroid


@Database(entities = [Asteroid::class ], version = 2 , exportSchema = false)
abstract class SingletonDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private lateinit var INSTANCE: SingletonDatabase

        fun getDatabase(context: Context): SingletonDatabase {
            synchronized(SingletonDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SingletonDatabase::class.java,
                        "MyDB.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}