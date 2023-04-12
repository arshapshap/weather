package com.example.main.data.repository

import com.example.main.data.location.LocationDataSource
import com.example.main.data.mapper.LocationInfoMapper
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val dataSource: LocationDataSource,
    private val mapper: LocationInfoMapper
) : LocationRepository {

    override fun getCurrentLocation(): LocationInfo? {
        val location = dataSource.getCurrentLocation()
        return location?.let { mapper.map(it) }
    }
}