package com.oborodulin.jwsuite.data_congregation.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalMemberDataSourceImpl @Inject constructor(
    private val memberDao: MemberDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalMemberDataSource {
    // Members by Congregation:
    override fun getFavoriteCongregationMembers(isService: Boolean) =
        memberDao.findByFavoriteCongregationAndIsServiceMark(isService)

    override fun getCongregationMembers(congregationId: UUID, isService: Boolean) =
        memberDao.findByCongregationId(congregationId, isService)

    // Members by Groups:
    override fun getFavoriteCongregationGroupMembers(isService: Boolean) =
        memberDao.findByFavoriteCongregationGroupAndIsServiceMark(isService)

    override fun getGroupMembers(groupId: UUID, isService: Boolean) =
        memberDao.findByGroupIdAndIsServiceMark(groupId, isService)

    override fun getEmptyGroupMembers(congregationId: UUID?, isService: Boolean) =
        memberDao.findDistinctByCongregationIdAndGroupIdIsNullAndIsServiceMark(
            congregationId,
            isService
        )

    // Member Roles:
    override fun getMemberRoles(memberId: UUID) = memberDao.findMemberRolesByMemberId(memberId)
    override fun getMemberRoles(pseudonym: String) = memberDao.findMemberRolesByPseudonym(pseudonym)

    // Roles:
    override fun getRoles(pseudonym: String) = memberDao.findRolesByPseudonym(pseudonym)
    override fun getRolesForMember(memberId: UUID) =
        memberDao.findRolesForMemberByMemberId(memberId)

    // Transfer Objects:
    override fun getRoleTransferObjects(pseudonym: String) =
        memberDao.findRoleTransferObjectByPseudonym(pseudonym)

    override fun getMemberRoleTransferObjects(roleId: UUID) =
        memberDao.findMemberRoleTransferObjectsByRoleId(roleId)

    // Member:
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
        memberDao.deleteByIdWithTotals(memberId)
    }

    override suspend fun deleteMembers(members: List<MemberEntity>) = withContext(dispatcher) {
        memberDao.delete(members)
    }

    override suspend fun deleteAllMembers() = withContext(dispatcher) {
        memberDao.deleteAll()
    }

    // Roles:
    override fun getMemberRole(memberRoleId: UUID) = memberDao.findMemberRoleById(memberRoleId)

    override suspend fun insertMemberRole(memberRole: MemberRoleEntity) =
        withContext(dispatcher) {
            memberDao.insert(memberRole)
        }

    override suspend fun updateMemberRole(memberRole: MemberRoleEntity) =
        withContext(dispatcher) {
            memberDao.update(memberRole)
        }

    override suspend fun deleteMemberRoleById(memberRoleId: UUID) = withContext(dispatcher) {
        memberDao.deleteMemberRoleById(memberRoleId)
    }

    // Movements:
    override suspend fun deleteMovementById(memberMovementId: UUID) = withContext(dispatcher) {
        memberDao.deleteMovementById(memberMovementId)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getMemberEntities(username: String?, byFavorite: Boolean) =
        memberDao.findEntitiesByUsernameAndFavoriteMark(username, byFavorite)

    override fun getMemberCongregationEntities(username: String?, byFavorite: Boolean) =
        memberDao.findMemberCongregationEntitiesByUsernameAndFavoriteMark(username, byFavorite)

    override fun getMemberMovementEntities(byFavorite: Boolean) =
        memberDao.findMemberMovementEntitiesByFavoriteMark(byFavorite)

    override fun getMemberRoleEntities(username: String?, byFavorite: Boolean) =
        memberDao.findMemberRoleEntitiesByUsernameAndFavoriteMark(username, byFavorite)

    override suspend fun loadMemberEntities(members: List<MemberEntity>) =
        withContext(dispatcher) {
            memberDao.insert(members)
        }

    override suspend fun loadMemberCongregationEntities(memberCongregations: List<MemberCongregationCrossRefEntity>) =
        withContext(dispatcher) {
            memberDao.insertCongregations(memberCongregations)
        }

    override suspend fun loadMemberMovementEntities(memberMovements: List<MemberMovementEntity>) =
        withContext(dispatcher) {
            memberDao.insertMovements(memberMovements)
        }

    override suspend fun loadMemberRoleEntities(memberRoles: List<MemberRoleEntity>) =
        withContext(dispatcher) {
            memberDao.insertMemberRoles(memberRoles)
        }
}