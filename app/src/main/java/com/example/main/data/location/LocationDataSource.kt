package com.example.main.data.location

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Tasks
import javax.inject.Inject

class LocationDataSource @Inject constructor(private val fusedLocationClient: FusedLocationProviderClient) {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Location? {
        return Tasks.await(
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token
            )
        )
    }
}