package com.example.skeleton.ui.fragment.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skeleton.common.Language
import com.example.skeleton.data.repository.SettingRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingViewModel(
    private val settingRepositoryImpl: SettingRepositoryImpl
) : ViewModel() {

    private var _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    val darkModeFlow = settingRepositoryImpl.enableDarkModeFlow

    fun toggleDarkMode(value: Boolean) {
        viewModelScope.launch {
            settingRepositoryImpl.setEnableDarkMode(value)
        }
    }


    init {
        observeLanguage()
    }


    private fun observeLanguage() {
        viewModelScope.launch {
            settingRepositoryImpl. languageFlow.collectLatest { language ->
                _uiState.value = _uiState.value.copy(selectedLanguage = language)
            }
        }
    }


    fun setLanguage() {
        viewModelScope.launch {
            val selectedLanguage = _uiState.value.selectedLanguage
            settingRepositoryImpl.setLanguage(selectedLanguage)
        }
    }

    fun changeLanguage(language: Language) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(selectedLanguage = language)
        }
    }
}
