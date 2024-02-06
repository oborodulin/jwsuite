package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group.GroupCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalGroupDataSource
import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(
    private val localGroupDataSource: LocalGroupDataSource,
    private val domainMappers: GroupMappers,
    private val csvMappers: GroupCsvMappers
) : GroupsRepository {
    override fun getAllByCongregation(congregationId: UUID) =
        localGroupDataSource.getCongregationGroups(congregationId)
            .map(domainMappers.groupViewListToGroupsListMapper::map)

    override fun getAllByFavoriteCongregation() =
        localGroupDataSource.getFavoriteCongregationGroups()
            .map(domainMappers.groupViewListToGroupsListMapper::map)

    override fun getNextGroupNum(congregationId: UUID) = flow {
        emit(localGroupDataSource.getNextGroupNum(congregationId))
    }

    override fun get(groupId: UUID) = localGroupDataSource.getGroup(groupId)
        .map(domainMappers.groupViewToGroupMapper::map)

    override fun save(group: Group) = flow {
        if (group.id == null) {
            localGroupDataSource.insertGroup(domainMappers.groupToGroupEntityMapper.map(group))
        } else {
            localGroupDataSource.updateGroup(domainMappers.groupToGroupEntityMapper.map(group))
        }
        emit(group)
    }

    override fun delete(group: Group) = flow {
        localGroupDataSource.deleteGroup(domainMappers.groupToGroupEntityMapper.map(group))
        this.emit(group)
    }

    override fun deleteById(groupId: UUID) = flow {
        localGroupDataSource.deleteGroupById(groupId)
        this.emit(groupId)
    }

    override suspend fun deleteAll() = localGroupDataSource.deleteAllGroups()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = GroupEntity.TABLE_NAME)
    override fun extractGroups(username: String?, byFavorite: Boolean) =
        localGroupDataSource.getGroupEntities(username, byFavorite)
            .map(csvMappers.groupEntityListToGroupCsvListMapper::map)

    @CsvLoad<GroupCsv>(
        fileNamePrefix = GroupEntity.TABLE_NAME,
        contentType = GroupCsv::class
    )
    override fun loadGroups(congregations: List<GroupCsv>) = flow {
        localGroupDataSource.loadGroupEntities(
            csvMappers.groupCsvListToGroupEntityListMapper.map(congregations)
        )
        emit(congregations.size)
    }
}