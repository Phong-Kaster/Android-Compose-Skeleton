package com.example.skeleton.domain.repository

import com.example.skeleton.common.Resource
import com.example.skeleton.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun observePosts(): Flow<Resource<List<Post>>>
    suspend fun refreshPosts()
}

