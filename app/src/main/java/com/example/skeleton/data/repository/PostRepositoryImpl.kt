package com.example.skeleton.data.repository

import com.example.skeleton.common.Resource
import com.example.skeleton.data.mapper.toDomain
import com.example.skeleton.data.remote.post.PostService
import com.example.skeleton.data.remote.util.safeApiCallFlow
import com.example.skeleton.domain.model.Post
import com.example.skeleton.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val api: PostService
) : PostRepository {

    override fun observePosts(): Flow<Resource<List<Post>>> =
        safeApiCallFlow {
            api.getPosts().map { it.toDomain() }
        }

    override suspend fun refreshPosts() {
        api.getPosts()
    }
}


