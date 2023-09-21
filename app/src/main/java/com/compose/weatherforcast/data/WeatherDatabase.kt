package com.compose.weatherforcast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compose.weatherforcast.model.Favorite
import com.compose.weatherforcast.model.UnitModel

@Database(entities = [Favorite::class, UnitModel::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}