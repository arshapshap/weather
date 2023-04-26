package com.example.main.domain.models

import java.io.Serializable

data class LocationInfo(
    val latitude: Double,
    val longitude: Double
) : Serializable