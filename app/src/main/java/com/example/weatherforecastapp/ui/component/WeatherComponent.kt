package com.example.weatherforecastapp.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.ui.theme.FavoriteBackgroundItem
import com.example.weatherforecastapp.ui.theme.YellowSun
import com.example.weatherforecastapp.ui.widgets.WeatherAppBar
import com.example.weatherforecastapp.util.OnAction
import com.example.weatherforecastapp.util.PassOnAction

@Composable
fun LoadingProgress(color: Color = Color.Unspecified) {
    MainContentBox (color = color) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}

@Composable
fun ErrorMessage(errorMessage: String? = "Error", color: Color = Color.Unspecified) {
    MainContentBox(color = color) {
        Text(text = "Something wrong happen", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Please, try again later", style = MaterialTheme.typography.bodyLarge)

        errorMessage?.let {
            Spacer(modifier = Modifier.height(20.dp))

            val annotatedMessage = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                    append("Error message: ")
                }

                withStyle(SpanStyle(fontWeight = FontWeight.Light)) {
                    append(errorMessage)
                }
            }

            Text(text = annotatedMessage, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun MainContentBox(color: Color = Color.Unspecified, compose: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        compose()
    }
}

@Composable
fun ToolBarIcon(image: ImageVector, description: String? = null, onAction: OnAction) {
    IconButton(onClick = onAction) {
        Icon(
            imageVector = image,
            contentDescription = description
        )
    }
}

@Composable
fun Empty() {
    Box {}
}

@Composable
fun WeatherStateImage(imageUrl: String = "https://openweathermap.org/img/wn/04d.png") {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Icon Image",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun WeatherInfoRow(compose: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        compose()
    }
}

@Composable
fun WeatherStatusRow(
    @DrawableRes id: Int = R.drawable.humidity,
    iconDescription: String? = null,
    text: String = "12%",
    startIcon: Boolean = true
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
    ) {
        if (startIcon) {
            WeatherStatusRowIcon(
                id = id,
                iconDescription = iconDescription
            )

            WeatherStatusRowText(text)
        } else {
            WeatherStatusRowText(text)

            WeatherStatusRowIcon(
                id = id,
                iconDescription = iconDescription
            )
        }
    }
}

@Composable
private fun WeatherStatusRowIcon(
    @DrawableRes id: Int,
    iconDescription: String? = null
) {
    Icon(
        painter = painterResource(id = id),
        contentDescription = iconDescription,
        modifier = Modifier.size(20.dp)
    )
}

@Composable
fun WeatherStatusRowText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(horizontal = 5.dp)
    )
}

@Composable
fun WeatherTagText(text: String, fontSize: TextUnit = 18.sp, color: Color = YellowSun) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = color,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(shape = RoundedCornerShape(20.dp))
    ) {
        Text(
            text = text.replaceFirstChar { it.titlecase() },
            fontSize = fontSize,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun MaxMinTemp(maxTemperature: String, minTemperature:  String) {
    Row {
        Text(
            text = "$maxTemperature°",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.Blue),
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "$minTemperature°",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.LightGray),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: PassOnAction<String> = {}
) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    Column {
        CommonTextField(
            valueState = searchQueryState,
            placeHolder = "Search",
            onAction = KeyboardActions {
                if (!valid) {
                    return@KeyboardActions
                } else {
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
                }
            }
        )
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeHolder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Search,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {
            Text(text = placeHolder)
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}

@Preview
@Composable
fun CityRow(
    favorite: Favorite = Favorite("Curitiba", "BR"),
    deleteAction: PassOnAction<Favorite> = {},
    clickAction: PassOnAction<String> = {}
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                clickAction(favorite.city)
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = FavoriteBackgroundItem
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = favorite.city,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 30.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            WeatherTagText(
                text = favorite.country,
                fontSize = TextUnit.Unspecified,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.width(70.dp))

            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Icon",
                modifier = Modifier
                    .clickable { deleteAction(favorite) },
                tint = Color.Red.copy(alpha = 0.3f),
            )

            Spacer(modifier = Modifier.width(30.dp))
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DefaultScaffold(
    title: String,
    padding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    compose: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = title,
                icon = Icons.Default.ArrowBack,
                navController = navController,
                isMainScreen = false
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(padding)
        ) {
            compose()
        }
    }
}

@Composable
fun CustomToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .fillMaxWidth(0.5f)
            .height(50.dp)
            .clip(RectangleShape)
            .background(color = Color.Magenta.copy(alpha = 0.4f))
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = Color.Black
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}