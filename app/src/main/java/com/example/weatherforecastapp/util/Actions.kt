package com.example.weatherforecastapp.util

typealias OnAction = () -> Unit
typealias PassOnAction<P> = (P) -> Unit
typealias BothOnAction<P, R> = (P) -> R
