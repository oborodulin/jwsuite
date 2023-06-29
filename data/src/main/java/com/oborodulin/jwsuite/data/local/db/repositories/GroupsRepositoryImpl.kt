package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.mappers.group.GroupMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGroupDataSource
import com.oborodulin.jwsuite.domain.model.Group
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(
    private val localGroupDataSource: LocalGroupDataSource,
    private val mappers: GroupMappers
) : GroupsRepository {
    override fun getAllByCongregation(congregationId: UUID) =
        localGroupDataSource.getCongregationGroups(congregationId)
            .map(mappers.groupViewListToGroupsListMapper::map)

    override fun getAllByFavoriteCongregation() =
        localGroupDataSource.getFavoriteCongregationGroups()
            .map(mappers.groupViewListToGroupsListMapper::map)

    override fun get(groupId: UUID) = localGroupDataSource.getGroup(groupId)
        .map(mappers.groupViewToGroupMapper::map)

    override fun save(group: Group) = flow {
        if (group.id == null) {
            localGroupDataSource.insertGroup(mappers.groupToGroupEntityMapper.map(group))
        } else {
            localGroupDataSource.updateGroup(mappers.groupToGroupEntityMapper.map(group))
        }
        emit(group)
    }

    override fun delete(group: Group) = flow {
        localGroupDataSource.deleteGroup(mappers.groupToGroupEntityMapper.map(group))
        this.emit(group)
    }

    override fun deleteById(groupId: UUID) = flow {
        localGroupDataSource.deleteGroupById(groupId)
        this.emit(groupId)
    }

    override suspend fun deleteAll() = localGroupDataSource.deleteAllGroups()
}