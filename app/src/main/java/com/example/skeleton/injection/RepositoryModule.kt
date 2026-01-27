package com.example.skeleton.injection

import com.example.skeleton.data.repository.SettingRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { SettingRepository(settingDatastore = get()) }
}