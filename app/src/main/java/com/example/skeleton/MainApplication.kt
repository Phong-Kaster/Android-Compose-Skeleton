package com.example.skeleton

import android.app.Application
import android.util.Log
import com.example.skeleton.injection.appModule
import com.example.skeleton.injection.databaseModule
import com.example.skeleton.injection.datastoreModule
import com.example.skeleton.injection.repositoryModule
import com.example.skeleton.injection.viewModelModule
import com.example.skeleton.ui.util.NotificationUtil
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * # [1. Skills For Real Engineers](https://github.com/mattpocock/skills)
 * # [2. 5 Agent Skills I Use Every Day](https://www.aihero.dev/5-agent-skills-i-use-every-day)
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        installUpdatedSecurityProvider()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }

        showColdStartNotification()
    }

    /**
     * Greets the user with a "hello world" notification on every cold start.
     *
     * Why here? `Application.onCreate()` runs exactly once per process creation — the very
     * definition of a cold start. Warm starts (returning from background) and screen
     * rotations reuse the living process, so this function is not called again and no
     * duplicate notification appears.
     *
     * If the user has not granted notification permission yet, [NotificationUtil] stays
     * silent — the Home screen's permission bottom sheet remains the place to grant it,
     * and the next cold start after granting shows the greeting.
     *
     * @author Phong-Kaster
     */
    private fun showColdStartNotification() {
        NotificationUtil.createChannel(context = this)
        NotificationUtil.postSimpleMessage(
            context = this,
            title = getString(R.string.skeleton),
            message = getString(R.string.hello_world),
        )
    }

    /**
     * Pulls an updated TLS provider from Google Play services when available.
     * Reduces certificate validation failures on older devices with stale system trust stores.
     */
    private fun installUpdatedSecurityProvider() {
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            Log.w(TAG, "Google Play services can be updated to improve TLS", e)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.w(TAG, "Google Play services not available; using platform TLS only", e)
        }
    }

    private companion object {
        private const val TAG = "MainApplication"
    }
}