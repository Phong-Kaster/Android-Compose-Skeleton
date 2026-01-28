package com.example.skeleton.ui.fragment.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skeleton.domain.model.UserAction
import com.example.skeleton.domain.repository.UserActionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(
    private val userActionRepository: UserActionRepository
): ViewModel(

){
    private val TAG = "HomeViewModel"
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        createUser()
    }

    fun createUser(){
        viewModelScope.launch(Dispatchers.IO) {
            val user = UserAction(
                id = 0,
                name = "John Doe",
                createdAt = Date().time
            )
            userActionRepository.saveAction(user)
            Log.d(TAG, "createUser")
        }
    }
}