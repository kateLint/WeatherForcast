@file:OptIn(ExperimentalMaterial3Api::class)

package com.compose.weatherforcast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.compose.weatherforcast.R
import com.compose.weatherforcast.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController){
    Scaffold(topBar = {
        WeatherAppBar(navController = navController, title = "About",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false)
    }) {
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Text(text = stringResource(id = R.string.about_app),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }



    Text(text ="About Screen....")
}