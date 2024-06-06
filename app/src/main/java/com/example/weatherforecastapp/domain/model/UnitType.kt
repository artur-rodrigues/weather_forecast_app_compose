package com.example.weatherforecastapp.domain.model

enum class UnitType(val value: String, val description: String, val choiceValue: Boolean) {
    IMPERIAL("imperial", "Imperial (°F)", true),
    METRIC("metric", "Metric (°C)", false);

    companion object {
        fun getUnitTypeByValue(value: String): UnitType {
            return when(value) {
                "imperial" -> IMPERIAL
                else -> METRIC
            }
        }

        fun getDefaultUnitType(): UnitType = IMPERIAL
    }
}