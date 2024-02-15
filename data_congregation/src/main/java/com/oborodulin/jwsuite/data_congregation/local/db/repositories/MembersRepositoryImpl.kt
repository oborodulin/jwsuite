package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCongregationCrossRefCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class MembersRepositoryImpl @Inject constructor(
    private val localMemberDataSource: LocalMemberDataSource,
    private val memberMappers: MemberMappers,
    private val roleMappers: RoleMappers,
    private val csvMemberMappers: MemberCsvMappers
) : MembersRepository {
    // Members by Congregation:
    override fun getAllByFavoriteCongregation(isService: Boolean) =
        localMemberDataSource.getFavoriteCongregationMembers(isService)
            .map(memberMappers.memberViewListToMembersListMapper::map)

    override fun getAllByCongregation(congregationId: UUID, isService: Boolean) =
        localMemberDataSource.getCongregationMembers(congregationId, isService)
            .map(memberMappers.memberViewListToMembersListMapper::map)

    // Members by Groups:
    override fun getAllByFavoriteCongregationGroup(isService: Boolean) =
        localMemberDataSource.getFavoriteCongregationGroupMembers(isService)
            .map(memberMappers.memberViewListToMembersListMapper::map)

    override fun getAllByGroup(groupId: UUID, isService: Boolean) =
        localMemberDataSource.getGroupMembers(groupId, isService)
            .map(memberMappers.memberViewListToMembersListMapper::map)

    // Member by Roles:
    override fun getAllByRoles(roleTypes: List<MemberRoleType>) =
        localMemberDataSource.getMembersByRoles(roleTypes)
            .map(memberMappers.memberViewListToMembersListMapper::map)

    // Member Roles:
    override fun getMemberRoles(memberId: UUID) = localMemberDataSource.getMemberRoles(memberId)
        .map(memberMappers.memberRoleViewListToMemberRolesListMapper::map)

    override fun getMemberRoles(pseudonym: String) = localMemberDataSource.getMemberRoles(pseudonym)
        .map(memberMappers.memberRoleViewListToMemberRolesListMapper::map)

    // Roles:
    override fun getRoles(pseudonym: String) = localMemberDataSource.getRoles(pseudonym)
        .map(roleMappers.roleEntityListToRolesListMapper::map)

    override fun getRolesForMember(memberId: UUID) =
        localMemberDataSource.getRolesForMember(memberId)
            .map(roleMappers.roleEntityListToRolesListMapper::map)

    // Transfer Objects:
    override fun getMemberTransferObjects(pseudonym: String) =
        localMemberDataSource.getRoleTransferObjects(pseudonym)
            .map(roleMappers.roleTransferObjectViewListToRoleTransferObjectsListMapper::map)

    override fun getRoleTransferObjects(roleId: UUID) =
        localMemberDataSource.getMemberRoleTransferObjects(roleId)
            .map(memberMappers.memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper::map)

    // Member:
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

    override fun delete(member: Member) = flow {
        localMemberDataSource.deleteMember(memberMappers.memberToMemberEntityMapper.map(member))
        this.emit(member)
    }

    override fun delete(memberId: UUID) = flow {
        localMemberDataSource.deleteMemberById(memberId)
        this.emit(memberId)
    }

    override suspend fun deleteAll() = localMemberDataSource.deleteAllMembers()

    // Member Role:
    override fun getMemberRole(memberRoleId: UUID) =
        localMemberDataSource.getMemberRole(memberRoleId)
            .map(memberMappers.memberRoleViewToMemberRoleMapper::map)

    override fun saveMemberRole(role: MemberRole) = flow {
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

    override fun deleteMemberRole(memberRoleId: UUID) = flow {
        localMemberDataSource.deleteMemberRoleById(memberRoleId)
        this.emit(memberRoleId)
    }

    override fun deleteMovementById(memberMovementId: UUID) = flow {
        localMemberDataSource.deleteMovementById(memberMovementId)
        this.emit(memberMovementId)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = MemberEntity.TABLE_NAME)
    override fun extractMembers(username: String?, byFavorite: Boolean) =
        localMemberDataSource.getMemberEntities(username, byFavorite)
            .map(csvMemberMappers.memberEntityListToMemberCsvListMapper::map)

    @CsvExtract(fileNamePrefix = MemberCongregationCrossRefEntity.TABLE_NAME)
    override fun extractMemberCongregations(username: String?, byFavorite: Boolean) =
        localMemberDataSource.getMemberCongregationEntities(username, byFavorite)
            .map(csvMemberMappers.memberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper::map)

    @CsvExtract(fileNamePrefix = MemberMovementEntity.TABLE_NAME)
    override fun extractMemberMovements(byFavorite: Boolean) =
        localMemberDataSource.getMemberMovementEntities(byFavorite)
            .map(csvMemberMappers.memberMovementEntityListToMemberMovementCsvListMapper::map)

    @CsvExtract(fileNamePrefix = MemberRoleEntity.TABLE_NAME)
    override fun extractMemberRoles(username: String?, byFavorite: Boolean) =
        localMemberDataSource.getMemberRoleEntities(username, byFavorite)
            .map(csvMemberMappers.memberRoleEntityListToMemberRoleCsvListMapper::map)

    @CsvLoad<MemberCsv>(fileNamePrefix = MemberEntity.TABLE_NAME, contentType = MemberCsv::class)
    override fun loadMembers(members: List<MemberCsv>) = flow {
        localMemberDataSource.loadMemberEntities(
            csvMemberMappers.memberCsvListToMemberEntityListMapper.map(members)
        )
        emit(members.size)
    }

    @CsvLoad<MemberCongregationCrossRefCsv>(
        fileNamePrefix = MemberCongregationCrossRefEntity.TABLE_NAME,
        contentType = MemberCongregationCrossRefCsv::class
    )
    override fun loadMemberCongregations(memberCongregations: List<MemberCongregationCrossRefCsv>) =
        flow {
            localMemberDataSource.loadMemberCongregationEntities(
                csvMemberMappers.memberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper.map(
                    memberCongregations
                )
            )
            emit(memberCongregations.size)
        }

    @CsvLoad<MemberMovementCsv>(
        fileNamePrefix = MemberMovementEntity.TABLE_NAME,
        contentType = MemberMovementCsv::class
    )
    override fun loadMemberMovements(memberMovements: List<MemberMovementCsv>) = flow {
        localMemberDataSource.loadMemberMovementEntities(
            csvMemberMappers.memberMovementCsvListToMemberMovementEntityListMapper.map(
                memberMovements
            )
        )
        emit(memberMovements.size)
    }

    @CsvLoad<MemberRoleCsv>(
        fileNamePrefix = MemberRoleEntity.TABLE_NAME,
        contentType = MemberRoleCsv::class
    )
    override fun loadMemberRoles(memberRoles: List<MemberRoleCsv>) = flow {
        localMemberDataSource.loadMemberRoleEntities(
            csvMemberMappers.memberRoleCsvListToMemberRoleEntityListMapper.map(memberRoles)
        )
        emit(memberRoles.size)
    }
}