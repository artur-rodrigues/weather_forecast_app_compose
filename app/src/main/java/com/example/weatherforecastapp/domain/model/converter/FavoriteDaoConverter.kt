package com.example.weatherforecastapp.domain.model.converter

import com.example.weatherforecastapp.data.model.FavoriteDao
import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.data.model.Result

fun Result.Success<List<FavoriteDao>>.convertToFavoriteResultSuccess(): Result<List<Favorite>> {
    return Result.Success(
        data.map {
            it.convertToFavorite()
        }
    )
}

fun Favorite.convertToFavoriteDao(): FavoriteDao {
    return FavoriteDao(city, country)
}

fun FavoriteDao.convertToFavorite(): Favorite {
    return Favorite(city, country)
}