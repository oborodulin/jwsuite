package com.oborodulin.jwsuite.data_congregation.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.TransferDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
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
    private val transferDao: TransferDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalMemberDataSource {
    // Members by Congregation:
    override fun getFavoriteCongregationMembers() = memberDao.findByFavoriteCongregation()
    override fun getCongregationMembers(congregationId: UUID) =
        memberDao.findByCongregationId(congregationId)

    override fun getFavoriteCongregationGroupMembers() = memberDao.findByFavoriteCongregationGroup()

    // Members by Groups:
    override fun getGroupMembers(groupId: UUID) = memberDao.findByGroupId(groupId)
    override fun getEmptyGroupMembers(congregationId: UUID?) =
        memberDao.findDistinctByCongregationIdAndGroupIdIsNull(congregationId)

    // Roles:
    override fun getMemberRoles(memberId: UUID) = memberDao.findMemberRolesByMemberId(memberId)
    override fun getMemberRoles(pseudonym: String) = memberDao.findMemberRolesByPseudonym(pseudonym)
    override fun getAllRoles() = memberDao.findAllRoles()
    override fun getRoles(pseudonym: String) = memberDao.findRolesByPseudonym(pseudonym)
    override fun getRolesForMember(memberId: UUID) =
        memberDao.findRolesForMemberByMemberId(memberId)

    // Transfer Objects:
    override fun getMemberTransferObjects(pseudonym: String) =
        transferDao.findByMemberPseudonym(pseudonym)

    override fun getRoleTransferObjects(roleId: UUID) = transferDao.findByRoleId(roleId)
    override fun getTransferObjectsForRole(roleId: UUID) =
        transferDao.findTransferObjectsForRoleByRoleId(roleId)

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
        memberDao.deleteById(memberId)
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
        memberDao.deleteRoleById(memberRoleId)
    }

    // Movements:
    override suspend fun deleteMovementById(memberMovementId: UUID) = withContext(dispatcher) {
        memberDao.deleteMovementById(memberMovementId)
    }

    // Transfer Objects:
    override suspend fun insertRoleTransferObject(roleTransferObject: RoleTransferObjectEntity) =
        withContext(dispatcher) {
            transferDao.insert(roleTransferObject)
        }

    override suspend fun updateRoleTransferObject(roleTransferObject: RoleTransferObjectEntity) =
        withContext(dispatcher) {
            transferDao.update(roleTransferObject)
        }

    override suspend fun deleteRoleTransferObjectById(roleTransferObjectId: UUID) =
        withContext(dispatcher) {
            transferDao.deleteRoleTransferObjectById(roleTransferObjectId)
        }
}
