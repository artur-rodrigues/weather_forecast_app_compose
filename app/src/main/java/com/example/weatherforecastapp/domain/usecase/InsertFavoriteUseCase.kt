package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.domain.model.converter.convertToFavoriteDao
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend fun add(favorite: Favorite) {
        repository.addFavorite(favorite.convertToFavoriteDao())
    }

    suspend fun update(favorite: Favorite) {
        repository.updateFavorite(favorite.convertToFavoriteDao())
    }
}
