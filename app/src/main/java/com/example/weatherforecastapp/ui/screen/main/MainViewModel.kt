package com.example.weatherforecastapp.ui.screen.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.domain.model.MainWeather
import com.example.weatherforecastapp.domain.model.UnitType
import com.example.weatherforecastapp.domain.usecase.GetFavoritesUseCase
import com.example.weatherforecastapp.domain.usecase.GetUnitsUseCase
import com.example.weatherforecastapp.domain.usecase.GetWeatherUseCase
import com.example.weatherforecastapp.domain.usecase.InsertFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val weatherUseCase: GetWeatherUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val favoriteInsertUseCase: InsertFavoriteUseCase,
    private val getUnitsUseCase: GetUnitsUseCase
) : ViewModel() {

    private val _favResult = MutableStateFlow<Result<List<Favorite>>>(Result.Loading)
    val favResult = _favResult.asStateFlow()
//    private lateinit var measureUnit: UnitType
    private val _unitType = mutableStateOf<UnitType?>(null)
    val unitType: State<UnitType?> = _unitType

    suspend fun getWeather(city: String): Result<MainWeather> {
        val ret = weatherUseCase.get(city, _unitType.value!!.value)

        if(ret is Result.Error) {
            ret.exception.run {
                Log.e("MainViewModel", "NetworkError: ${this.message}", this)
            }
        }

        return ret
    }

    init {
        getAllUnits()
    }

    private fun getAllUnits() {
        viewModelScope.launch {
            getUnitsUseCase.get().distinctUntilChanged()
                .collect {
                    _unitType.value = when(it) {
                        is Result.Success -> UnitType.getUnitTypeByValue(it.data[0].unit)
                        else -> UnitType.getDefaultUnitType()
                    }
                    getAllFavorites()
                }
        }
    }

    private fun getAllFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase.get().distinctUntilChanged()
                .collect {
                    _favResult.value = it
                }
        }
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteInsertUseCase.add(favorite)
        }
    }
}