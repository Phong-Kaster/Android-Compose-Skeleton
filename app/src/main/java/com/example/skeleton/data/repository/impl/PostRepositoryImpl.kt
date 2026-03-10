package com.example.skeleton.data.repository.impl

import com.example.skeleton.common.Resource
import com.example.skeleton.data.database.local.dao.PostDao
import com.example.skeleton.data.mapper.toDomain
import com.example.skeleton.data.mapper.toEntity
import com.example.skeleton.data.remote.api.PostApi
import com.example.skeleton.domain.model.Post
import com.example.skeleton.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val api: PostApi,
    private val dao: PostDao,
) : PostRepository {

    override fun observePosts(): Flow<List<Post>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun refreshPosts(): Resource<Unit> {
        return try {
            val posts = api.getPosts().map { it.toDomain() }
            dao.insertAll(posts.map { it.toEntity() })
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }
}
