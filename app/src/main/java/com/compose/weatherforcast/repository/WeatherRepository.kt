package com.compose.weatherforcast.repository

import android.util.Log
import com.compose.weatherforcast.data.DataOrException
import com.compose.weatherforcast.model.Weather
import com.compose.weatherforcast.model.WeatherObject
import com.compose.weatherforcast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String)
            : DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)

        }catch (e: Exception){
            Log.d("TAG", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("TAG", "getWeather: $response")
        return  DataOrException(data = response)

    }

}