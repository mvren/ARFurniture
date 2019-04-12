package com.uist.arfurniture

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.net.Uri.fromParts
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import android.content.Intent
import android.net.Uri
import android.provider.Settings


object CameraPermissionHelper {
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA
    private val CAMERA_PERMISSION_CODE = 0

    /**
     * Check to see we have the necessary permissions for this app.
     */
    fun hasCameraPermission(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(activity, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Check to see we have the necessary permissions for this app, and ask for them if we don't.
     */
    fun requestCameraPermission(activity: Activity) {
        ActivityCompat.requestPermissions(activity, arrayOf(CAMERA_PERMISSION),
            CAMERA_PERMISSION_CODE)
    }

    fun shouldShowRequestPermissionRationale(activity: Activity): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION)
    }

    fun launchPermissionSettings(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(intent)
    }
}