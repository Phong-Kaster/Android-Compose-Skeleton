package com.example.skeleton.ui.fragment.home.component

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun HomeRequestPermission(
    enable: Int,
    onNotificationGranted: () -> Unit = {},
    onLocationGranted: () -> Unit = {}
) {
    val context = LocalContext.current

    var showBottomSheet by remember { mutableStateOf(false) }

    var allowOpenNotificationSettings by remember { mutableStateOf(false) }
    var allowOpenLocationSettings by remember { mutableStateOf(false) }

    /**
     * Notification permission launcher (Android 13+)
     */
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onNotificationGranted()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                !shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                allowOpenNotificationSettings = true
            }
            showBottomSheet = true
        }
    }

    /**
     * Location permission launcher
     */
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onLocationGranted()
        } else {
            if (!shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                allowOpenLocationSettings = true
            }
            showBottomSheet = true
        }
    }

    /**
     * Re-check permissions whenever [enable] changes
     */
    LaunchedEffect(enable) {
        val notificationGranted = isNotificationGranted(context)
        val locationGranted = isLocationGranted(context)

        showBottomSheet = !notificationGranted || !locationGranted

        allowOpenNotificationSettings = false
        allowOpenLocationSettings = false
    }

    /**
     * Re-check when returning from Settings
     */
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            val notificationGranted = isNotificationGranted(context)
            val locationGranted = isLocationGranted(context)

            showBottomSheet = !notificationGranted || !locationGranted
        }
    }

    /**
     * Request Notification
     */
    fun requestNotification() {
        if (allowOpenNotificationSettings) {
            openAppSettings(context)
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            // Pre-Android 13: granted by default
            onNotificationGranted()
        }
    }

    /**
     * Request Location
     */
    fun requestLocation() {
        if (allowOpenLocationSettings) {
            openAppSettings(context)
        } else {
            locationPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     * Bottom Sheet
     */
    HomePermissionBottomSheet(
        enable = showBottomSheet,
        isNotificationEnable = isNotificationGranted(context),
        isLocationEnable = isLocationGranted(context),
        onDismiss = { showBottomSheet = false },
        onGrantNotification = { requestNotification() },
        onGrantLocation = { requestLocation() }
    )
}

/**
 * Check if we should show permission rationale.
 */
private fun shouldShowRequestPermissionRationale(context: Context, permission: String): Boolean {
    return if (context is androidx.fragment.app.FragmentActivity) {
        ActivityCompat.shouldShowRequestPermissionRationale(
            context,
            permission
        )
    } else {
        false
    }
}

fun isNotificationGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}

fun isLocationGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}