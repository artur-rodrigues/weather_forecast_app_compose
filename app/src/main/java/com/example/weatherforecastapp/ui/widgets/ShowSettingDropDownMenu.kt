package com.example.weatherforecastapp.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherforecastapp.ui.navigation.WeatherScreens

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {

    var expanded by remember {
        mutableStateOf(true)
    }


    val items = listOf("About", "Favorites", "Settings")
    val icons = listOf(Icons.Default.Info, Icons.Default.FavoriteBorder, Icons.Default.Settings)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)

    ) {

        val dismiss = {
            expanded = false
            showDialog.value = false
        }

        val navigate = { rote: String ->
            navController.navigate(rote)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = dismiss,
            modifier = Modifier
                .width(140.dp)
                .background(color = Color.White)
        ) {

            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            fontWeight = FontWeight.W300
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = "$text icon",
                            tint = Color.LightGray
                        )
                    },
                    onClick = {
                        when(index) {
                            0 -> navigate(WeatherScreens.ABOUT.screen)
                            1 -> navigate(WeatherScreens.FAVORITE.screen)
                            2 -> navigate(WeatherScreens.SETTINGS.screen)
                            else -> Unit
                        }

                        dismiss()
                    })
            }
        }
    }
}