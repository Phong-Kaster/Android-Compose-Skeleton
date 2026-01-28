package com.example.skeleton.data.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.skeleton.data.database.local.converter.DateConverter
import com.example.skeleton.data.database.local.dao.UserActionDao
import com.example.skeleton.data.database.local.entity.UserActionEntity

@Database(
    entities = [
        UserActionEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userActionDao(): UserActionDao
}
