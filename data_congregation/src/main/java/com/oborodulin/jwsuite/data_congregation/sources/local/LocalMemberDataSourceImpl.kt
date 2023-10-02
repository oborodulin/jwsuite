package com.oborodulin.jwsuite.data_congregation.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
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
    override fun getMemberRoles(memberId: UUID) = memberDao.findRolesByMemberId(memberId)
    override fun getRolesForMember(memberId: UUID) =
        memberDao.findRolesForMemberByMemberId(memberId)

    override fun getMember(memberId: UUID) = memberDao.findDistinctById(memberId)
    override suspend fun insertMember(
        member: MemberEntity,
        memberCongregation: MemberCongregationCrossRefEntity,
        memberMovement: MemberMovementEntity
    ) = withContext(dispatcher) {
        memberDao.insert(member, memberCongregation, memberMovement)
    }

    override suspend fun updateMember(
        member: MemberEntity,
        memberCongregation: MemberCongregationCrossRefEntity,
        memberMovement: MemberMovementEntity
    ) = withContext(dispatcher) {
        memberDao.update(member, memberCongregation, memberMovement)
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

    override suspend fun insertMemberRole(memberRole: MemberRoleCrossRefEntity) =
        withContext(dispatcher) {
            memberDao.insert(memberRole)
        }

    override suspend fun updateMemberRole(memberRole: MemberRoleCrossRefEntity) =
        withContext(dispatcher) {
            memberDao.update(memberRole)
        }

    override suspend fun deleteRoleById(memberRoleId: UUID) = withContext(dispatcher) {
        memberDao.deleteRoleById(memberRoleId)
    }

    override suspend fun deleteMovementById(memberMovementId: UUID) = withContext(dispatcher) {
        memberDao.deleteMovementById(memberMovementId)
    }
}
