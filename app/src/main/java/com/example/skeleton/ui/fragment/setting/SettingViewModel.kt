package com.example.skeleton.ui.fragment.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skeleton.common.Language
import com.example.skeleton.data.repository.SettingRepository
import com.example.skeleton.ui.fragment.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    val darkModeFlow = settingRepository.enableDarkModeFlow

    fun toggleDarkMode(value: Boolean) {
        viewModelScope.launch {
            settingRepository.setEnableDarkMode(value)
        }
    }



    val languageFlow = settingRepository.languageFlow
    fun setLanguage(language: Language) {
        viewModelScope.launch {
            settingRepository.setLanguage(language)
        }
    }
}
