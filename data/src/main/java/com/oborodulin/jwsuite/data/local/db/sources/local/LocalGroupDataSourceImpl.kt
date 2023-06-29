package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGroupDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGroupDataSourceImpl @Inject constructor(
    private val groupDao: GroupDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGroupDataSource {
    override fun getCongregationGroups(congregationId: UUID) =
        groupDao.findByCongregationId(congregationId)

    override fun getFavoriteCongregationGroups() = groupDao.findByFavoriteCongregation()

    override fun getGroup(groupId: UUID) = groupDao.findDistinctById(groupId)

    override suspend fun insertGroup(group: GroupEntity) = withContext(dispatcher) {
        groupDao.insert(group)
    }

    override suspend fun updateGroup(group: GroupEntity) = withContext(dispatcher) {
        groupDao.update(group)
    }

    override suspend fun deleteGroup(group: GroupEntity) = withContext(dispatcher) {
        groupDao.delete(group)
    }

    override suspend fun deleteGroupById(groupId: UUID) = withContext(dispatcher) {
        groupDao.deleteById(groupId)
    }

    override suspend fun deleteGroups(groups: List<GroupEntity>) = withContext(dispatcher) {
        groupDao.delete(groups)
    }

    override suspend fun deleteAllGroups() = withContext(dispatcher) {
        groupDao.deleteAll()
    }

}
