package com.example.main.domain.usecases

import com.example.main.domain.models.LocationInfo
import com.example.main.domain.repository.LocationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {

    fun execute(): LocationInfo? {
        return locationRepository.getCurrentLocation()
    }
}