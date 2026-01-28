package com.example.skeleton.injection

import androidx.room.Room
import com.example.skeleton.data.database.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    // Room Database (singleton)
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    // DAO
    single { get<AppDatabase>().userActionDao() }
}