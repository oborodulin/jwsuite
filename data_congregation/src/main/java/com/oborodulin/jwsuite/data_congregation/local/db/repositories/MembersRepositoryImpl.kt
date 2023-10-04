package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.domain.model.Member
import com.oborodulin.jwsuite.domain.model.MemberRole
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class MembersRepositoryImpl @Inject constructor(
    private val localMemberDataSource: LocalMemberDataSource,
    private val mappers: MemberMappers
) : MembersRepository {
    override fun getAllByFavoriteCongregation() =
        localMemberDataSource.getFavoriteCongregationMembers()
            .map(mappers.memberViewListToMembersListMapper::map)

    override fun getAllByCongregation(congregationId: UUID) =
        localMemberDataSource.getCongregationMembers(congregationId)
            .map(mappers.memberViewListToMembersListMapper::map)

    override fun getAllByFavoriteCongregationGroup() =
        localMemberDataSource.getFavoriteCongregationGroupMembers()
            .map(mappers.memberViewListToMembersListMapper::map)

    override fun getAllByGroup(groupId: UUID) = localMemberDataSource.getGroupMembers(groupId)
        .map(mappers.memberViewListToMembersListMapper::map)

    override fun getMemberRoles(memberId: UUID) = localMemberDataSource.getMemberRoles(memberId)
        .map(mappers.memberRoleViewListToMemberRolesListMapper::map)

    override fun getRolesForMember(memberId: UUID) =
        localMemberDataSource.getRolesForMember(memberId)
            .map(mappers.roleEntityListToRolesListMapper::map)

    override fun get(memberId: UUID) = localMemberDataSource.getMember(memberId)
        .map(mappers.memberViewToMemberMapper::map)

    override fun save(member: Member) = flow {
        if (member.id == null) {
            localMemberDataSource.insertMember(
                mappers.memberToMemberEntityMapper.map(member),
                mappers.memberToMemberCongregationCrossRefEntityMapper.map(member),
                mappers.memberToMemberMovementEntityMapper.map(member)
            )
        } else {
            localMemberDataSource.updateMember(
                mappers.memberToMemberEntityMapper.map(member),
                mappers.memberToMemberCongregationCrossRefEntityMapper.map(member),
                mappers.memberToMemberMovementEntityMapper.map(member)
            )
        }
        emit(member)
    }

    override fun saveRole(role: MemberRole) = flow {
        if (role.id == null) {
            localMemberDataSource.insertMemberRole(
                mappers.memberRoleToMemberRoleEntityMapper.map(role)
            )
        } else {
            localMemberDataSource.updateMemberRole(
                mappers.memberRoleToMemberRoleEntityMapper.map(role)
            )
        }
        emit(role)
    }

    override fun delete(member: Member) = flow {
        localMemberDataSource.deleteMember(mappers.memberToMemberEntityMapper.map(member))
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