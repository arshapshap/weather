package com.example.main.presentation.providers

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.main.R

private const val PERMISSION_REQUEST_CODE = 1

class PermissionsRequestProvider(
    private val activity: AppCompatActivity,
    private val onSinglePermissionHandled: (Boolean) -> Unit
) {

    private val requestSinglePermission = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        onSinglePermissionHandled.invoke(isGranted)
    }

    fun checkPermission(permission: String) =
        (ActivityCompat.checkSelfPermission(
            activity,
            permission
        ) == PermissionChecker.PERMISSION_GRANTED)

    fun requestPermission(permission: String) {
        requestSinglePermission.launch(permission)
    }
}