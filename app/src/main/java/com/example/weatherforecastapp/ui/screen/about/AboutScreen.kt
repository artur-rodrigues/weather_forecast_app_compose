package com.example.weatherforecastapp.ui.screen.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.ui.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AboutScreen(navController: NavController = rememberNavController()) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "About",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(id = R.string.about_app),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(id = R.string.app_api),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}