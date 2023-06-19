package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.GroupEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGroupDataSource {
    fun getCongregationGroups(congregationId: UUID): Flow<List<GroupEntity>>
    fun getGroup(groupId: UUID): Flow<GroupEntity>
    suspend fun insertGroup(group: GroupEntity)
    suspend fun updateGroup(group: GroupEntity)
    suspend fun deleteGroup(group: GroupEntity)
    suspend fun deleteGroupById(groupId: UUID)
    suspend fun deleteGroups(groups: List<GroupEntity>)
    suspend fun deleteAllGroups()
}