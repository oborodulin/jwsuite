package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class MembersRepositoryImpl @Inject constructor(
    private val localMemberDataSource: LocalMemberDataSource,
    private val memberMappers: MemberMappers,
    private val transferObjectMappers: TransferObjectMappers
) : MembersRepository {
    override fun getAllByFavoriteCongregation() =
        localMemberDataSource.getFavoriteCongregationMembers()
            .map(memberMappers.memberViewListToMembersListMapper::map)

    override fun getAllByCongregation(congregationId: UUID) =
        localMemberDataSource.getCongregationMembers(congregationId)
            .map(memberMappers.memberViewListToMembersListMapper::map)

    override fun getAllByFavoriteCongregationGroup() =
        localMemberDataSource.getFavoriteCongregationGroupMembers()
            .map(memberMappers.memberViewListToMembersListMapper::map)

    override fun getAllByGroup(groupId: UUID) = localMemberDataSource.getGroupMembers(groupId)
        .map(memberMappers.memberViewListToMembersListMapper::map)

    override fun getMemberRoles(memberId: UUID) = localMemberDataSource.getMemberRoles(memberId)
        .map(memberMappers.memberRoleViewListToMemberRolesListMapper::map)

    override fun getMemberRoles(pseudonym: String) = localMemberDataSource.getMemberRoles(pseudonym)
        .map(memberMappers.memberRoleViewListToMemberRolesListMapper::map)

    override fun getRoles(pseudonym: String) = localMemberDataSource.getRoles(pseudonym)
    .map(memberMappers.roleEntityListToRolesListMapper::map)
    override fun getRolesForMember(memberId: UUID) =
        localMemberDataSource.getRolesForMember(memberId)
            .map(memberMappers.roleEntityListToRolesListMapper::map)

    override fun getMemberTransferObjects(pseudonym: String) =
        localMemberDataSource.getMemberTransferObjects(pseudonym)
            .map(transferObjectMappers.memberRoleTransferObjectViewListToRoleTransferObjectsListMapper::map)

    override fun getRoleTransferObjects(roleId: UUID) =
        localMemberDataSource.getRoleTransferObjects(roleId)
            .map(transferObjectMappers.memberRoleTransferObjectViewListToRoleTransferObjectsListMapper::map)

    override fun getTransferObjectsForRole(roleId: UUID) =
        localMemberDataSource.getTransferObjectsForRole(roleId)
            .map(transferObjectMappers.transferObjectListToTransferObjectsListMapper::map)

    override fun get(memberId: UUID) = localMemberDataSource.getMember(memberId)
        .map(memberMappers.memberViewToMemberMapper::map)

    override fun save(member: Member) = flow {
        if (member.id == null) {
            localMemberDataSource.insertMember(
                memberMappers.memberToMemberEntityMapper.map(member),
                memberMappers.memberToMemberCongregationCrossRefEntityMapper.map(member),
                memberMappers.memberToMemberMovementEntityMapper.map(member)
            )
        } else {
            localMemberDataSource.updateMember(
                memberMappers.memberToMemberEntityMapper.map(member),
                memberMappers.memberToMemberCongregationCrossRefEntityMapper.map(member),
                memberMappers.memberToMemberMovementEntityMapper.map(member)
            )
        }
        emit(member)
    }

    override fun saveRole(role: MemberRole) = flow {
        if (role.id == null) {
            localMemberDataSource.insertMemberRole(
                memberMappers.memberRoleToMemberRoleEntityMapper.map(role)
            )
        } else {
            localMemberDataSource.updateMemberRole(
                memberMappers.memberRoleToMemberRoleEntityMapper.map(role)
            )
        }
        emit(role)
    }

    override fun delete(member: Member) = flow {
        localMemberDataSource.deleteMember(memberMappers.memberToMemberEntityMapper.map(member))
        this.emit(member)
    }

    override fun deleteById(memberId: UUID) = flow {
        localMemberDataSource.deleteMemberById(memberId)
        this.emit(memberId)
    }

    override suspend fun deleteAll() = localMemberDataSource.deleteAllMembers()

    override fun deleteRoleById(memberRoleId: UUID) = flow {
        localMemberDataSource.deleteRoleById(memberRoleId)
        this.emit(memberRoleId)
    }

    override fun deleteMovementById(memberMovementId: UUID) = flow {
        localMemberDataSource.deleteMovementById(memberMovementId)
        this.emit(memberMovementId)
    }
}