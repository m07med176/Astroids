package com.udacity.asteroidradar.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object{
        @RequiresApi(Build.VERSION_CODES.N)
        fun getToday(): String  = formatDate(Calendar.getInstance().time)


        @RequiresApi(Build.VERSION_CODES.N)
        private fun getNextWeak(): String {
            Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 7)
                return formatDate(this.time)
            }
        }
        @RequiresApi(Build.VERSION_CODES.N)
        private fun formatDate(date: Date): String  = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(date)

    }

}