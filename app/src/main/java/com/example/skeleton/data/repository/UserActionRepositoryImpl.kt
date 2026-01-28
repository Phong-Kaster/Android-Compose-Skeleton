package com.example.skeleton.data.repository

import com.example.skeleton.data.database.local.dao.UserActionDao
import com.example.skeleton.data.database.mapper.toDomain
import com.example.skeleton.data.database.mapper.toEntity
import com.example.skeleton.domain.model.UserAction
import com.example.skeleton.domain.repository.UserActionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserActionRepositoryImpl(
    private val dao: UserActionDao
) : UserActionRepository {

    override fun observeActions(): Flow<List<UserAction>> {
        return dao.getAll()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun saveAction(action: UserAction) {
        dao.insert(action.toEntity())
    }

    override suspend fun clear() {
        dao.clear()
    }
}
