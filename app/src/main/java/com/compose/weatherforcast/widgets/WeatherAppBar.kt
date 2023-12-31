@file:OptIn(ExperimentalMaterial3Api::class)

package com.compose.weatherforcast.widgets

import android.content.Context
import android.graphics.drawable.Icon
import android.icu.text.CaseMap.Title
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.weatherforcast.model.Favorite
import com.compose.weatherforcast.navigation.WeatherScreens
import com.compose.weatherforcast.screens.favorites.FavoritesViewModel
import androidx.compose.ui.platform.LocalContext



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar (
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoritesViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}

){
    val context = LocalContext.current

    val showIt = remember {
        mutableStateOf(false)
    }

    val showDialog = remember {
        mutableStateOf(false)
    }

    if(showDialog.value){
        ShowSettingDropDownMenu(showDialog, navController)
    }

    TopAppBar(title = { Text(text = title,
        color = Color.Black,
        style = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 15.sp),
    )},
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent),
        actions = {
                  if(isMainScreen){
                      IconButton(onClick = {
                          onAddActionClicked.invoke()
                      }) {
                          Icon(
                              imageVector = Icons.Default.Search,
                              contentDescription = "Search Icon"
                          )
                      }
                      IconButton(onClick = {
                         // onButtonClicked.invoke()
                          showDialog.value = true
                      }) {
                         Icon(
                              imageVector = Icons.Rounded.MoreVert,
                              contentDescription = "More Icon"
                          )
                      }

                  }else{
                      Box {

                      }
                  }
        },
        navigationIcon = {
            if( icon != null){
                Icon(imageVector = icon, contentDescription = null, tint = Color.Black,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
            if(isMainScreen){
                val isAlreadyFavList = favoriteViewModel
                    .favlist.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }
                if(isAlreadyFavList.isNullOrEmpty()){
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = dataList[0],
                                        country = dataList[1]
                                    )

                                )

                                .run {

                                    showIt.value = true
                                }

                            }
                        , tint = Color.Red.copy(alpha = 0.6f)
                    )
                }else{
                   // showIt.value = false
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel.deleteFavorite(  Favorite(
                                    city = dataList[0],
                                    country = dataList[1]
                                )
                                ).run {
                                    showIt.value = false
                                }
                            }
                        , tint = Color.Red.copy(alpha = 0.6f)
                    )
                }
                ShowToast(context = context, showIt)
            }
        }
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, " Added to Favorites",
            Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    var items = listOf("About", "Favorites", "Settings")
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)) {
        DropdownMenu(modifier = Modifier
            .width(140.dp)
            .background(Color.White), expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(text = {

                    Row {
                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.Favorite
                                else -> Icons.Default.Settings

                            }, contentDescription = null,
                            tint = Color.LightGray
                        )

                        Text(text = text,
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    when (text){
                                        "About" -> WeatherScreens.AboutScreen.name
                                        "Favorites" -> WeatherScreens.FavoriteScreen.name
                                        else -> WeatherScreens.SettingsScreen.name
                                    }

                                )
                            })
                    }

                }, onClick = {
                    expanded = false
                    showDialog.value = false
                })

            }
        }
    }
}
