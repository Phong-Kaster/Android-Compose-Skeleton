package com.example.skeleton.injection

import com.example.skeleton.data.repository.SettingRepositoryImpl
import com.example.skeleton.data.repository.UserActionRepositoryImpl
import com.example.skeleton.domain.repository.SettingRepository
import com.example.skeleton.domain.repository.UserActionRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<SettingRepository> { SettingRepositoryImpl(settingDatastore = get()) }

    single<UserActionRepository> { UserActionRepositoryImpl(dao = get()) }
}