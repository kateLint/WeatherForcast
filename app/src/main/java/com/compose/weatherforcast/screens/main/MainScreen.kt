package com.compose.weatherforcast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.compose.weatherforcast.R
import com.compose.weatherforcast.data.DataOrException
import com.compose.weatherforcast.model.Weather
import com.compose.weatherforcast.model.WeatherItem
import com.compose.weatherforcast.utils.formatDate
import com.compose.weatherforcast.utils.formatDecimals
import com.compose.weatherforcast.widgets.HumidityWindPressureRow
import com.compose.weatherforcast.widgets.SunsetSunRiseRow
import com.compose.weatherforcast.widgets.WeatherAppBar
import com.compose.weatherforcast.widgets.WeatherDetailRow
import com.compose.weatherforcast.widgets.WeatherStateImage

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()){

    var isImperial by remember {
        mutableStateOf(false)
    }

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ){
        value = mainViewModel.getWeatherData(city = "Seattle", "")
    }.value

    if(weatherData.loading == true){
        CircularProgressIndicator()
    }else if(weatherData.data != null){
       MainScaffold(weather = weatherData.data!!, navController,
           isImperial = isImperial)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(  weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold( topBar = {
        Surface(shadowElevation = 5.dp) {
            WeatherAppBar(navController = navController, title = weather.city.name + ", ${weather.city.country}",
                icon = Icons.Default.ArrowBack,
                elevation = 5.dp){
                Log.d("TAG", "MainScaffold: Button Cliked")

            }
        }
        
    }) {
       
        MainContent(modifier = Modifier.padding(top =  it.calculateTopPadding()), data=weather, isImperial = isImperial)
    }

}

@Composable
fun MainContent(modifier: Modifier, data: Weather, isImperial: Boolean) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"



    Column(modifier = modifier
        .padding(4.dp)
        .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(modifier = Modifier.padding(6.dp),text = formatDate(data.list[0].dt),
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold)

            Surface(modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape,
                color =  Color(0xFFEEF1EF)
            ) {

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    WeatherStateImage(imageUrl = imageUrl)
                    Text(text = formatDecimals(data.list[0].temp.day) + "F", style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(text = data.list[0].weather[0].main, fontStyle = FontStyle.Italic)
                }
            }
            HumidityWindPressureRow(weather = data.list[0], isImperial)
            Divider()
            SunsetSunRiseRow(weather = data.list[0])
        Text(text = "This Week",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                items(items = data.list){item: WeatherItem ->
                    WeatherDetailRow(item)

                }
            }

        }


        }
}

