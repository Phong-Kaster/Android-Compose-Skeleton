package com.example.skeleton.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skeleton.common.Resource
import com.example.skeleton.domain.model.UserAction
import com.example.skeleton.domain.repository.PostRepository
import com.example.skeleton.domain.repository.UserActionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(
    private val userActionRepository: UserActionRepository,
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observePosts()
        refreshPosts()
    }

    /** Observes posts from local DB (offline-first). */
    private fun observePosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.observePosts().collect { posts ->
                _uiState.update { it.copy(posts = posts, refreshError = null) }
            }
        }
    }

    /** Fetches from API and saves to DB. Call on init or pull-to-refresh. */
    fun refreshPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isRefreshing = true, refreshError = null) }
            when (val result = postRepository.refreshPosts()) {
                is Resource.Success -> _uiState.update {
                    it.copy(isRefreshing = false, refreshError = null)
                }
                is Resource.Error -> _uiState.update {
                    it.copy(isRefreshing = false, refreshError = result.message)
                }
                is Resource.Loading -> { /* kept in isRefreshing */ }
            }
        }
    }

    fun createUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = UserAction(
                id = 0,
                name = "John Doe",
                createdAt = Date().time
            )
            userActionRepository.saveAction(user)
        }
    }
}
