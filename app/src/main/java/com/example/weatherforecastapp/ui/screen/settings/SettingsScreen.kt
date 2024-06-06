package com.example.weatherforecastapp.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Unit
import com.example.weatherforecastapp.domain.model.UnitType
import com.example.weatherforecastapp.ui.component.CustomToggleButton
import com.example.weatherforecastapp.ui.component.DefaultScaffold
import com.example.weatherforecastapp.ui.component.ErrorMessage
import com.example.weatherforecastapp.ui.component.LoadingProgress
import com.example.weatherforecastapp.ui.theme.SettingsButton
import com.example.weatherforecastapp.util.PassOnAction

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val result = viewModel.unitResult.collectAsState().value

    viewModel.unitInsertResult.observe(LocalLifecycleOwner.current) {
        navController.popBackStack()
    }

    result.run {
        when(this) {
            is Result.Success -> {
                val defaultUnitType = if(data.isEmpty()) {
                    UnitType.getDefaultUnitType()
                } else {
                    UnitType.getUnitTypeByValue(data[0].unit)
                }

                MainContent(
                    unitType = defaultUnitType,
                    navController = navController,
                    viewModel = viewModel)
            }
            Result.Loading -> LoadingProgress()
            is Result.Error -> ErrorMessage(apiErrorResponse?.message ?: exception.message)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MainContent(
    unitType: UnitType,
    navController: NavController,
    viewModel: SettingsViewModel
) {
    var unitToggleState by remember {
        mutableStateOf(unitType.choiceValue)
    }

    var choiceState by remember {
        mutableStateOf(unitType)
    }

    val onCheckedChanged: PassOnAction<Boolean> = {
        unitToggleState = !it

        choiceState = when(unitToggleState) {
            true -> UnitType.IMPERIAL
            false -> UnitType.METRIC
        }
    }

    DefaultScaffold(
        title = "Settings",
        navController = navController
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Change Units of Measurement",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomToggleButton(
                checked = !unitToggleState,
                onCheckedChange = onCheckedChanged
            ) {
                Text(text = choiceState.description)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.removeAllUnit()
                    viewModel.insertUnit(Unit(choiceState.value))
                },
                colors = ButtonDefaults.buttonColors(containerColor = SettingsButton)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Save",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}