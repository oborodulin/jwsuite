package com.oborodulin.jwsuite.data_congregation.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationMemberCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.local.LocalCongregationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalCongregationDataSourceImpl @Inject constructor(
    private val congregationDao: CongregationDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalCongregationDataSource {
    // Congregations:
    override fun getCongregations() = congregationDao.findDistinctAll()

    override fun getCongregation(congregationId: UUID) =
        congregationDao.findDistinctById(congregationId)

    override fun getFavoriteCongregation() = congregationDao.findDistinctFavorite()

    override suspend fun insertCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.insertWithFavorite(congregation)
        }

    override suspend fun updateCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.updateWithFavorite(congregation)
        }

    override suspend fun deleteCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.delete(congregation)
        }

    override suspend fun deleteCongregationById(congregationId: UUID) = withContext(dispatcher) {
        congregationDao.deleteById(congregationId)
    }

    override suspend fun deleteCongregations(congregations: List<CongregationEntity>) =
        withContext(dispatcher) {
            congregationDao.delete(congregations)
        }

    override suspend fun deleteAllCongregations() = withContext(dispatcher) {
        congregationDao.deleteAll()
    }

    override suspend fun makeFavoriteCongregationById(congregationId: UUID) =
        withContext(dispatcher) {
            congregationDao.makeFavoriteById(congregationId)
        }

    // Members:
    override suspend fun insertMember(
        congregation: CongregationEntity, member: MemberEntity, activityDate: OffsetDateTime
    ) = withContext(dispatcher) {
        congregationDao.insert(congregation, member, activityDate)
    }

    override suspend fun updateMember(congregationMember: CongregationMemberCrossRefEntity) =
        withContext(dispatcher) {
            congregationDao.update(congregationMember)
        }

    override suspend fun deleteMember(congregationMember: CongregationMemberCrossRefEntity) =
        withContext(dispatcher) {
            congregationDao.deleteMember(congregationMember)
        }

    override suspend fun deleteMember(congregationMemberId: UUID) = withContext(dispatcher) {
        congregationDao.deleteMemberById(congregationMemberId)
    }

    override suspend fun deleteMembers(congregationId: UUID) = withContext(dispatcher) {
        congregationDao.deleteMembersByCongregationId(congregationId)
    }
}
