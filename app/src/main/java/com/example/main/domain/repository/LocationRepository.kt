package com.example.main.domain.repository

import com.example.main.domain.models.LocationInfo

interface LocationRepository {

    fun getCurrentLocation(): LocationInfo?
}