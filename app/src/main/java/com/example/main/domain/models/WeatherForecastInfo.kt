package com.example.main.domain.models

import java.io.Serializable

data class WeatherForecastInfo(
    val list: List<WeatherInfo>
) : Serializable