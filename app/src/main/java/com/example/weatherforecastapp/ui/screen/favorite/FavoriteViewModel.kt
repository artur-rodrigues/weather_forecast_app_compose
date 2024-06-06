package com.example.weatherforecastapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.domain.usecase.GetFavoritesUseCase
import com.example.weatherforecastapp.domain.usecase.InsertFavoriteUseCase
import com.example.weatherforecastapp.domain.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getUseCase: GetFavoritesUseCase,
    private val insertUseCase: InsertFavoriteUseCase,
    private val removeUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _favResult = MutableStateFlow<Result<List<Favorite>>>(Result.Loading)
    val favResult = _favResult.asStateFlow()

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() {
        viewModelScope.launch {
            getUseCase.get().distinctUntilChanged()
                .collect {
                    _favResult.value = it
                }
        }
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            insertUseCase.add(favorite)
        }
    }

    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch {
            insertUseCase.update(favorite)
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            removeUseCase.remove(favorite)
        }
    }

    fun removeAllFavorite() {
        viewModelScope.launch {
            removeUseCase.removeAll()
        }
    }
}