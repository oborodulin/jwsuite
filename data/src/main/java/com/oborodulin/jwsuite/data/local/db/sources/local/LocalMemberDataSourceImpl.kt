package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalMemberDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalMemberDataSourceImpl @Inject constructor(
    private val memberDao: MemberDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalMemberDataSource {
    override fun getFavoriteCongregationMembers() = memberDao.findByFavoriteCongregation()
    override fun getCongregationMembers(congregationId: UUID) =
        memberDao.findByCongregationId(congregationId)

    override fun getFavoriteCongregationGroupMembers() = memberDao.findByFavoriteCongregationGroup()
    override fun getGroupMembers(groupId: UUID) = memberDao.findByGroupId(groupId)
    override fun getMember(memberId: UUID) = memberDao.findDistinctById(memberId)
    override suspend fun insertMember(member: MemberEntity) = withContext(dispatcher) {
        memberDao.insert(member)
    }

    override suspend fun updateMember(member: MemberEntity) = withContext(dispatcher) {
        memberDao.update(member)
    }

    override suspend fun deleteMember(member: MemberEntity) = withContext(dispatcher) {
        memberDao.delete(member)
    }

    override suspend fun deleteMemberById(memberId: UUID) = withContext(dispatcher) {
        memberDao.deleteById(memberId)
    }

    override suspend fun deleteMembers(members: List<MemberEntity>) = withContext(dispatcher) {
        memberDao.delete(members)
    }

    override suspend fun deleteAllMembers() = withContext(dispatcher) {
        memberDao.deleteAll()
    }

}
