package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectMappers
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
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class MembersRepositoryImpl @Inject constructor(
    private val localMemberDataSource: LocalMemberDataSource,
    private val memberMappers: MemberMappers,
    private val transferObjectMappers: TransferObjectMappers,
    private val csvMemberMappers: MemberCsvMappers,
    private val csvRoleMappers: RoleCsvMappers,
    private val csvTransferObjectMappers: TransferObjectCsvMappers
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

    // Member Roles:
    override fun getMemberRoles(memberId: UUID) = localMemberDataSource.getMemberRoles(memberId)
        .map(memberMappers.memberRoleViewListToMemberRolesListMapper::map)

    override fun getMemberRoles(pseudonym: String) = localMemberDataSource.getMemberRoles(pseudonym)
        .map(memberMappers.memberRoleViewListToMemberRolesListMapper::map)

    // Roles:
    override fun getAllRoles() = localMemberDataSource.getAllRoles()
        .map(memberMappers.roleEntityListToRolesListMapper::map)

    override fun getRoles(pseudonym: String) = localMemberDataSource.getRoles(pseudonym)
        .map(memberMappers.roleEntityListToRolesListMapper::map)

    override fun getRolesForMember(memberId: UUID) =
        localMemberDataSource.getRolesForMember(memberId)
            .map(memberMappers.roleEntityListToRolesListMapper::map)

    // Transfer Objects:
    override fun getMemberTransferObjects(pseudonym: String) =
        localMemberDataSource.getMemberTransferObjects(pseudonym)
            .map(transferObjectMappers.roleTransferObjectViewListToRoleTransferObjectsListMapper::map)

    override fun getRoleTransferObjects(roleId: UUID) =
        localMemberDataSource.getRoleTransferObjects(roleId)
            .map(transferObjectMappers.memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper::map)

    override fun getTransferObjectsForRole(roleId: UUID) =
        localMemberDataSource.getTransferObjectsForRole(roleId)
            .map(transferObjectMappers.transferObjectEntityListToTransferObjectsListMapper::map)

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

    override fun deleteById(memberId: UUID) = flow {
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

    override fun deleteMemberRoleById(memberRoleId: UUID) = flow {
        localMemberDataSource.deleteMemberRoleById(memberRoleId)
        this.emit(memberRoleId)
    }

    override fun deleteMovementById(memberMovementId: UUID) = flow {
        localMemberDataSource.deleteMovementById(memberMovementId)
        this.emit(memberMovementId)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = MemberEntity.TABLE_NAME)
    override fun extractMembers() = localMemberDataSource.getMemberEntities()
        .map(csvMemberMappers.memberEntityListToMemberCsvListMapper::map)

    @CsvExtract(fileNamePrefix = MemberCongregationCrossRefEntity.TABLE_NAME)
    override fun extractMemberCongregations() =
        localMemberDataSource.getMemberCongregationEntities()
            .map(csvMemberMappers.memberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper::map)

    @CsvExtract(fileNamePrefix = MemberMovementEntity.TABLE_NAME)
    override fun extractMemberMovements() = localMemberDataSource.getMemberMovementEntities()
        .map(csvMemberMappers.memberMovementEntityListToMemberMovementCsvListMapper::map)

    @CsvExtract(fileNamePrefix = RoleEntity.TABLE_NAME)
    override fun extractRoles() = localMemberDataSource.getRoleEntities()
        .map(csvRoleMappers.roleEntityListToRoleCsvListMapper::map)

    @CsvExtract(fileNamePrefix = MemberRoleEntity.TABLE_NAME)
    override fun extractMemberRoles() = localMemberDataSource.getMemberRoleEntities()
        .map(csvMemberMappers.memberRoleEntityListToMemberRoleCsvListMapper::map)

    @CsvExtract(fileNamePrefix = TransferObjectEntity.TABLE_NAME)
    override fun extractTransferObjects() = localMemberDataSource.getTransferObjectEntities()
        .map(csvTransferObjectMappers.transferObjectEntityListToTransferObjectCsvListMapper::map)

    @CsvExtract(fileNamePrefix = RoleTransferObjectEntity.TABLE_NAME)
    override fun extractRoleTransferObjects() =
        localMemberDataSource.getRoleTransferObjectEntities()
            .map(csvMemberMappers.roleTransferObjectEntityListToRoleTransferObjectCsvListMapper::map)

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

    @CsvLoad<RoleCsv>(fileNamePrefix = RoleEntity.TABLE_NAME, contentType = RoleCsv::class)
    override fun loadRoles(roles: List<RoleCsv>) = flow {
        localMemberDataSource.loadRoleEntities(
            csvRoleMappers.roleCsvListToRoleEntityListMapper.map(roles)
        )
        emit(roles.size)
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

    @CsvLoad<TransferObjectCsv>(
        fileNamePrefix = TransferObjectEntity.TABLE_NAME,
        contentType = TransferObjectCsv::class
    )
    override fun loadTransferObjects(transferObjects: List<TransferObjectCsv>) = flow {
        localMemberDataSource.loadTransferObjectEntities(
            csvTransferObjectMappers.transferObjectCsvListToTransferObjectEntityListMapper.map(
                transferObjects
            )
        )
        emit(transferObjects.size)
    }

    @CsvLoad<RoleTransferObjectCsv>(
        fileNamePrefix = RoleTransferObjectEntity.TABLE_NAME,
        contentType = RoleTransferObjectCsv::class
    )
    override fun loadRoleTransferObjects(roleTransferObjects: List<RoleTransferObjectCsv>) = flow {
        localMemberDataSource.loadRoleTransferObjectEntities(
            csvMemberMappers.roleTransferObjectCsvListToRoleTransferObjectEntityListMapper.map(
                roleTransferObjects
            )
        )
        emit(roleTransferObjects.size)
    }
}