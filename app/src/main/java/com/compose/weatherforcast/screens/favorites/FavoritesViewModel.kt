package com.compose.weatherforcast.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.weatherforcast.model.Favorite
import com.compose.weatherforcast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: WeatherDbRepository):
        ViewModel(){
    private val _favList = MutableStateFlow<List<Favorite>>(listOf())
    val favlist = _favList.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect{listOfFav->
                    if(listOfFav.isEmpty()){
                        Log.d("TAG", "Empty: ")
                    }else{
                        _favList.value = listOfFav
                        Log.d("TAG", "${favlist.value}: ")
                    }
                }
        }
    }
    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
    }

}