package com.example.main.presentation.providers

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker

class PermissionsRequestProvider(
    private val activity: AppCompatActivity,
    private val onSinglePermissionHandled: (Boolean) -> Unit
) {

    private val requestSinglePermission =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onSinglePermissionHandled.invoke(isGranted)
        }

    fun checkPermission(permission: String) = (ActivityCompat.checkSelfPermission(
        activity, permission
    ) == PermissionChecker.PERMISSION_GRANTED)

    fun requestPermission(permission: String) {
        requestSinglePermission.launch(permission)
    }
}