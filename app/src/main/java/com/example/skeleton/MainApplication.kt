package com.example.skeleton

import android.app.Application
import com.example.skeleton.injection.appModule
import com.example.skeleton.injection.databaseModule
import com.example.skeleton.injection.datastoreModule
import com.example.skeleton.injection.repositoryModule
import com.example.skeleton.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Logging
//            logger(Level.INFO)

            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}