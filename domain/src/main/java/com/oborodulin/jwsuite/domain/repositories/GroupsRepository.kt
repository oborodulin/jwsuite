package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Group
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GroupsRepository {
    fun getAllByCongregation(congregationId: UUID): Flow<List<Group>>
    fun getAllByFavoriteCongregation(): Flow<List<Group>>
    fun getNextGroupNum(congregationId: UUID): Flow<Int>
    fun get(groupId: UUID): Flow<Group>
    fun save(group: Group): Flow<Group>
    fun delete(group: Group): Flow<Group>
    fun deleteById(groupId: UUID): Flow<UUID>
    suspend fun deleteAll()
}