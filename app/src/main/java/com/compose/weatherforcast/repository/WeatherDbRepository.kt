package com.compose.weatherforcast.repository

import com.compose.weatherforcast.data.WeatherDao
import com.compose.weatherforcast.model.Favorite
import com.compose.weatherforcast.model.UnitModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)

    fun getUnits(): Flow<List<UnitModel>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: UnitModel) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: UnitModel) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: UnitModel) = weatherDao.deleteUnit(unit)
}