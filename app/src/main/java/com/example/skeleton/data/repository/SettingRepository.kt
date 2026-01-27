package com.example.skeleton.data.repository


import com.example.skeleton.common.Language
import com.example.skeleton.data.datastore.SettingDatastore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow


class SettingRepository(
    private val settingDatastore: SettingDatastore
) {

    val enableIntroFlow = settingDatastore.enableIntroFlow
    val enableLanguageIntroFlow = settingDatastore.enableLanguageIntroFlow
    val languageFlow = settingDatastore.languageFlow
    val enableDarkModeFlow = settingDatastore.enableDarkModeFlow

    suspend fun setEnableIntro(value: Boolean) = settingDatastore.setEnableIntro(value)

    suspend fun setEnableLanguageIntro(value: Boolean) = settingDatastore.setEnableLanguageIntro(value)

    suspend fun setLanguage(language: Language) = settingDatastore.setLanguage(language)

    suspend fun setEnableDarkMode(value: Boolean) = settingDatastore.setEnableDarkMode(value)
}
