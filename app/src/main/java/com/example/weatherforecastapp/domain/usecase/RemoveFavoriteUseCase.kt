package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.domain.model.converter.convertToFavoriteDao
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend fun remove(favorite: Favorite) {
        repository.deleteFavorite(favorite.convertToFavoriteDao())
    }

    suspend fun removeAll() {
        repository.deleteAllFavorites()
    }
}
