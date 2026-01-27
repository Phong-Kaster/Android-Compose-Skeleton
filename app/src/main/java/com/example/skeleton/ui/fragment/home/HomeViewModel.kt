package com.example.skeleton.ui.fragment.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel(){

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
}