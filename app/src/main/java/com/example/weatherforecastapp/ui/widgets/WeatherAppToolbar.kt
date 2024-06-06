package com.example.weatherforecastapp.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastapp.ui.component.Empty
import com.example.weatherforecastapp.ui.component.ToolBarIcon
import com.example.weatherforecastapp.util.OnAction

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    iconTint: Color = Color.Unspecified,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController = rememberNavController(),
    onAddActionClicked: OnAction = {},
    onButtonClicked: OnAction = {},
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(start = 5.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        },
        actions = {
            if(isMainScreen) {
                ToolBarIcon(image = Icons.Default.Search, description = "Search Icon") {
                    onAddActionClicked()
                }

                ToolBarIcon(image = Icons.Rounded.MoreVert, description = "More Options") {
                    showDialog.value = true
                }
            } else {
                Empty()
            }
        },
        navigationIcon = {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable {
                            onButtonClicked()
                        }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.shadow(elevation = elevation)
    )
}