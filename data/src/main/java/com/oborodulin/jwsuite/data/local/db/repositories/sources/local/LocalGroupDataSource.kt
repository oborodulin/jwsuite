package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data.local.db.views.GroupView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGroupDataSource {
    fun getCongregationGroups(congregationId: UUID): Flow<List<GroupView>>
    fun getFavoriteCongregationGroups(): Flow<List<GroupView>>
    fun getGroup(groupId: UUID): Flow<GroupView>
    suspend fun insertGroup(group: GroupEntity)
    suspend fun updateGroup(group: GroupEntity)
    suspend fun deleteGroup(group: GroupEntity)
    suspend fun deleteGroupById(groupId: UUID)
    suspend fun deleteGroups(groups: List<GroupEntity>)
    suspend fun deleteAllGroups()
}