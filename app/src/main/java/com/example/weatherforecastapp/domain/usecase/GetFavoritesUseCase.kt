package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.domain.model.converter.convertToFavorite
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: WeatherRepository
) : RoomGetUseCase<String, Favorite>() {

    override fun get(): Flow<Result<List<Favorite>>> {
        return internalGet(repository.getFavorites()) {
            it.convertToFavorite()
        }
    }

    override suspend fun find(key: String): Result<Favorite> {
        return internalFind(repository.getFavoriteById(key)) {
            it.convertToFavorite()
        }
    }
}
