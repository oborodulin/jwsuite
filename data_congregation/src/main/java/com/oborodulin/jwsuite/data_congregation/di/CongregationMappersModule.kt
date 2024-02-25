package com.oborodulin.jwsuite.data_congregation.di

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationCsvListToCongregationEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationCsvToCongregationEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationEntityListToCongregationCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationEntityToCongregationCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.total.CongregationTotalCsvListToCongregationTotalEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.total.CongregationTotalCsvToCongregationTotalEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.total.CongregationTotalEntityListToCongregationTotalCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.total.CongregationTotalEntityToCongregationTotalCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group.GroupCsvListToGroupEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group.GroupCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group.GroupCsvToGroupEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group.GroupEntityListToGroupCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group.GroupEntityToGroupCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberCsvListToMemberEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberCsvToMemberEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberEntityListToMemberCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberEntityToMemberCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.congregation.MemberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.congregation.MemberCongregationCrossRefCsvToMemberCongregationCrossRefEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.congregation.MemberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.congregation.MemberCongregationCrossRefEntityToMemberCongregationCrossRefCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.movement.MemberMovementCsvListToMemberMovementEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.movement.MemberMovementCsvToMemberMovementEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.movement.MemberMovementEntityListToMemberMovementCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.movement.MemberMovementEntityToMemberMovementCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role.MemberRoleCsvListToMemberRoleEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role.MemberRoleCsvToMemberRoleEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role.MemberRoleEntityListToMemberRoleCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role.MemberRoleEntityToMemberRoleCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleCsvListToRoleEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleCsvToRoleEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleEntityListToRoleCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleEntityToRoleCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.transfer.RoleTransferObjectCsvListToRoleTransferObjectEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.transfer.RoleTransferObjectCsvToRoleTransferObjectEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.transfer.RoleTransferObjectEntityListToRoleTransferObjectCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.transfer.RoleTransferObjectEntityToRoleTransferObjectCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectCsvListToTransferObjectEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectCsvToTransferObjectEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectEntityListToTransferObjectCsvListMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectEntityToTransferObjectCsvMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationEntityToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationToCongregationEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationTotalViewToCongregationTotalsMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewListToCongregationsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationsListToCongregationEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.FavoriteCongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupToGroupEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupViewListToGroupsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupViewToGroupMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupsListToGroupEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberToMemberEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewListToMembersListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MembersListToMemberEntityListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberCongregationCrossRefEntityToMemberCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberLastCongregationViewToMemberCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberToMemberCongregationCrossRefEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberLastMovementViewToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberMovementEntityToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberToMemberMovementEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleToMemberRoleEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToMemberRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewToMemberRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewToRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectToRoleTransferObjectEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewListToTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewToTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleEntityListToRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleEntityToRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.transfer.RoleTransferObjectViewListToRoleTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.transfer.RoleTransferObjectViewToRoleTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectEntityListToTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectEntityToTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CongregationMappersModule {
    // MAPPERS:
    // Congregations:
    @Singleton
    @Provides
    fun provideCongregationEntityToCongregationMapper(): CongregationEntityToCongregationMapper =
        CongregationEntityToCongregationMapper()

    @Singleton
    @Provides
    fun provideCongregationViewToCongregationMapper(
        localityMapper: LocalityViewToGeoLocalityMapper,
        congregationMapper: CongregationEntityToCongregationMapper
    ): CongregationViewToCongregationMapper = CongregationViewToCongregationMapper(
        localityMapper = localityMapper,
        congregationMapper = congregationMapper
    )

    @Singleton
    @Provides
    fun provideCongregationViewListToCongregationsListMapper(mapper: CongregationViewToCongregationMapper): CongregationViewListToCongregationsListMapper =
        CongregationViewListToCongregationsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideFavoriteCongregationViewToCongregationMapper(mapper: CongregationViewToCongregationMapper): FavoriteCongregationViewToCongregationMapper =
        FavoriteCongregationViewToCongregationMapper(congregationViewMapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationToCongregationEntityMapper(): CongregationToCongregationEntityMapper =
        CongregationToCongregationEntityMapper()

    @Singleton
    @Provides
    fun provideCongregationsListToCongregationEntityListMapper(mapper: CongregationToCongregationEntityMapper): CongregationsListToCongregationEntityListMapper =
        CongregationsListToCongregationEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationTotalViewToCongregationTotalsMapper(mapper: FavoriteCongregationViewToCongregationMapper): CongregationTotalViewToCongregationTotalsMapper =
        CongregationTotalViewToCongregationTotalsMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationMappers(
        congregationViewListToCongregationsListMapper: CongregationViewListToCongregationsListMapper,
        congregationViewToCongregationMapper: CongregationViewToCongregationMapper,
        congregationsListToCongregationEntityListMapper: CongregationsListToCongregationEntityListMapper,
        congregationToCongregationEntityMapper: CongregationToCongregationEntityMapper,
        favoriteCongregationViewToCongregationMapper: FavoriteCongregationViewToCongregationMapper,
        congregationTotalViewToCongregationTotalsMapper: CongregationTotalViewToCongregationTotalsMapper
    ): CongregationMappers = CongregationMappers(
        congregationViewListToCongregationsListMapper,
        congregationViewToCongregationMapper,
        congregationsListToCongregationEntityListMapper,
        congregationToCongregationEntityMapper,
        favoriteCongregationViewToCongregationMapper,
        congregationTotalViewToCongregationTotalsMapper
    )

    // Groups:
    @Singleton
    @Provides
    fun provideGroupViewToGroupMapper(mapper: CongregationViewToCongregationMapper): GroupViewToGroupMapper =
        GroupViewToGroupMapper(congregationMapper = mapper)

    @Singleton
    @Provides
    fun provideGroupViewListToGroupsListMapper(mapper: GroupViewToGroupMapper): GroupViewListToGroupsListMapper =
        GroupViewListToGroupsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGroupToGroupEntityMapper(): GroupToGroupEntityMapper = GroupToGroupEntityMapper()

    @Singleton
    @Provides
    fun provideGroupsListToGroupEntityListMapper(mapper: GroupToGroupEntityMapper): GroupsListToGroupEntityListMapper =
        GroupsListToGroupEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGroupMappers(
        groupViewListToGroupsListMapper: GroupViewListToGroupsListMapper,
        groupViewToGroupMapper: GroupViewToGroupMapper,
        groupsListToGroupEntityListMapper: GroupsListToGroupEntityListMapper,
        groupToGroupEntityMapper: GroupToGroupEntityMapper
    ): GroupMappers = GroupMappers(
        groupViewListToGroupsListMapper,
        groupViewToGroupMapper,
        groupsListToGroupEntityListMapper,
        groupToGroupEntityMapper
    )

    // Members:
    // Member Congregation:
    @Singleton
    @Provides
    fun provideMemberToMemberCongregationCrossRefEntityMapper(): MemberToMemberCongregationCrossRefEntityMapper =
        MemberToMemberCongregationCrossRefEntityMapper()

    @Singleton
    @Provides
    fun provideMemberCongregationCrossRefEntityToMemberCongregationMapper(): MemberCongregationCrossRefEntityToMemberCongregationMapper =
        MemberCongregationCrossRefEntityToMemberCongregationMapper()

    @Singleton
    @Provides
    fun provideMemberLastCongregationViewToMemberCongregationMapper(mapper: MemberCongregationCrossRefEntityToMemberCongregationMapper): MemberLastCongregationViewToMemberCongregationMapper =
        MemberLastCongregationViewToMemberCongregationMapper(mapper = mapper)

    // Member Movement:
    @Singleton
    @Provides
    fun provideMemberMovementEntityToMemberMovementMapper(): MemberMovementEntityToMemberMovementMapper =
        MemberMovementEntityToMemberMovementMapper()

    @Singleton
    @Provides
    fun provideMemberLastMovementViewToMemberMovementMapper(mapper: MemberMovementEntityToMemberMovementMapper): MemberLastMovementViewToMemberMovementMapper =
        MemberLastMovementViewToMemberMovementMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberToMemberMovementEntityMapper(): MemberToMemberMovementEntityMapper =
        MemberToMemberMovementEntityMapper()

    // Member:
    @Singleton
    @Provides
    fun provideMemberViewToMemberMapper(
        localityMapper: LocalityViewToGeoLocalityMapper,
        congregationMapper: CongregationEntityToCongregationMapper,
        groupMapper: GroupViewToGroupMapper,
        lastCongregationMapper: MemberLastCongregationViewToMemberCongregationMapper,
        movementMapper: MemberLastMovementViewToMemberMovementMapper
    ): MemberViewToMemberMapper = MemberViewToMemberMapper(
        localityMapper = localityMapper,
        congregationMapper = congregationMapper,
        groupMapper = groupMapper,
        lastCongregationMapper = lastCongregationMapper,
        movementMapper = movementMapper
    )

    @Singleton
    @Provides
    fun provideMemberViewListToMembersListMapper(mapper: MemberViewToMemberMapper): MemberViewListToMembersListMapper =
        MemberViewListToMembersListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberToMemberEntityMapper(): MemberToMemberEntityMapper =
        MemberToMemberEntityMapper()

    @Singleton
    @Provides
    fun provideMembersListToMemberEntityListMapper(mapper: MemberToMemberEntityMapper): MembersListToMemberEntityListMapper =
        MembersListToMemberEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberMappers(
        memberViewListToMembersListMapper: MemberViewListToMembersListMapper,
        memberViewToMemberMapper: MemberViewToMemberMapper,
        membersListToMemberEntityListMapper: MembersListToMemberEntityListMapper,
        memberToMemberEntityMapper: MemberToMemberEntityMapper,
        memberToMemberCongregationCrossRefEntityMapper: MemberToMemberCongregationCrossRefEntityMapper,
        memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper,
        memberLastMovementViewToMemberMovementMapper: MemberLastMovementViewToMemberMovementMapper,
        roleEntityListToRolesListMapper: RoleEntityListToRolesListMapper,
        memberRoleViewToMemberRoleMapper: MemberRoleViewToMemberRoleMapper,
        memberRoleViewListToMemberRolesListMapper: MemberRoleViewListToMemberRolesListMapper,
        memberRoleViewListToRolesListMapper: MemberRoleViewListToRolesListMapper,
        memberRoleToMemberRoleEntityMapper: MemberRoleToMemberRoleEntityMapper,
        memberRoleTransferObjectToRoleTransferObjectEntityMapper: MemberRoleTransferObjectToRoleTransferObjectEntityMapper,
        memberRoleTransferObjectViewToTransferObjectMapper: MemberRoleTransferObjectViewToTransferObjectMapper,
        memberRoleTransferObjectViewListToTransferObjectsListMapper: MemberRoleTransferObjectViewListToTransferObjectsListMapper,
        memberRoleTransferObjectViewToMemberRoleTransferObjectMapper: MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper,
        memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper: MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
    ): MemberMappers = MemberMappers(
        memberViewListToMembersListMapper,
        memberViewToMemberMapper,
        membersListToMemberEntityListMapper,
        memberToMemberEntityMapper,
        memberToMemberCongregationCrossRefEntityMapper,
        memberToMemberMovementEntityMapper,
        memberLastMovementViewToMemberMovementMapper,
        roleEntityListToRolesListMapper,
        memberRoleViewToMemberRoleMapper,
        memberRoleViewListToMemberRolesListMapper,
        memberRoleViewListToRolesListMapper,
        memberRoleToMemberRoleEntityMapper,
        memberRoleTransferObjectToRoleTransferObjectEntityMapper,
        memberRoleTransferObjectViewToTransferObjectMapper,
        memberRoleTransferObjectViewListToTransferObjectsListMapper,
        memberRoleTransferObjectViewToMemberRoleTransferObjectMapper,
        memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
    )

    // Role:
    @Singleton
    @Provides
    fun provideRoleEntityToRoleMapper(): RoleEntityToRoleMapper = RoleEntityToRoleMapper()

    @Singleton
    @Provides
    fun provideRoleEntityListToRolesListMapper(mapper: RoleEntityToRoleMapper): RoleEntityListToRolesListMapper =
        RoleEntityListToRolesListMapper(mapper = mapper)

    // Member Role:
    @Singleton
    @Provides
    fun provideMemberRoleViewToRoleMapper(mapper: RoleEntityToRoleMapper): MemberRoleViewToRoleMapper =
        MemberRoleViewToRoleMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleViewListToRolesListMapper(mapper: MemberRoleViewToRoleMapper): MemberRoleViewListToRolesListMapper =
        MemberRoleViewListToRolesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleViewToMemberRoleMapper(
        memberMapper: MemberViewToMemberMapper, roleMapper: RoleEntityToRoleMapper
    ): MemberRoleViewToMemberRoleMapper =
        MemberRoleViewToMemberRoleMapper(memberMapper = memberMapper, roleMapper = roleMapper)

    @Singleton
    @Provides
    fun provideMemberRoleViewListToMemberRolesListMapper(mapper: MemberRoleViewToMemberRoleMapper): MemberRoleViewListToMemberRolesListMapper =
        MemberRoleViewListToMemberRolesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleToMemberRoleEntityMapper(): MemberRoleToMemberRoleEntityMapper =
        MemberRoleToMemberRoleEntityMapper()

    // Transfer Object:
    @Singleton
    @Provides
    fun provideTransferObjectEntityToTransferObjectMapper(): TransferObjectEntityToTransferObjectMapper =
        TransferObjectEntityToTransferObjectMapper()

    @Singleton
    @Provides
    fun provideTransferObjectEntityListToTransferObjectsListMapper(mapper: TransferObjectEntityToTransferObjectMapper): TransferObjectEntityListToTransferObjectsListMapper =
        TransferObjectEntityListToTransferObjectsListMapper(mapper = mapper)

    // Role Transfer Object:
    @Singleton
    @Provides
    fun provideRoleTransferObjectViewToRoleTransferObjectMapper(mapper: TransferObjectEntityToTransferObjectMapper): RoleTransferObjectViewToRoleTransferObjectMapper =
        RoleTransferObjectViewToRoleTransferObjectMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoleTransferObjectViewListToRoleTransferObjectsListMapper(mapper: RoleTransferObjectViewToRoleTransferObjectMapper): RoleTransferObjectViewListToRoleTransferObjectsListMapper =
        RoleTransferObjectViewListToRoleTransferObjectsListMapper(mapper = mapper)

    // Member Role Transfer Object:
    @Singleton
    @Provides
    fun provideMemberRoleTransferObjectViewToTransferObjectMapper(mapper: TransferObjectEntityToTransferObjectMapper): MemberRoleTransferObjectViewToTransferObjectMapper =
        MemberRoleTransferObjectViewToTransferObjectMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleTransferObjectViewListToTransferObjectsListMapper(mapper: MemberRoleTransferObjectViewToTransferObjectMapper): MemberRoleTransferObjectViewListToTransferObjectsListMapper =
        MemberRoleTransferObjectViewListToTransferObjectsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleTransferObjectViewToMemberRoleTransferObjectMapper(mapper: RoleTransferObjectViewToRoleTransferObjectMapper): MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper =
        MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper(mapper: MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper): MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper =
        MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleTransferObjectToRoleTransferObjectEntityMapper(): MemberRoleTransferObjectToRoleTransferObjectEntityMapper =
        MemberRoleTransferObjectToRoleTransferObjectEntityMapper()

    // TransferObject:
    @Singleton
    @Provides
    fun provideTransferObjectMappers(
        transferObjectEntityToTransferObjectMapper: TransferObjectEntityToTransferObjectMapper,
        transferObjectEntityListToTransferObjectsListMapper: TransferObjectEntityListToTransferObjectsListMapper
    ): TransferObjectMappers = TransferObjectMappers(
        transferObjectEntityToTransferObjectMapper,
        transferObjectEntityListToTransferObjectsListMapper
    )

    // Role:
    @Singleton
    @Provides
    fun provideRoleMappers(
        roleEntityListToRolesListMapper: RoleEntityListToRolesListMapper,
        transferObjectEntityListToTransferObjectsListMapper: TransferObjectEntityListToTransferObjectsListMapper,
        roleTransferObjectViewListToRoleTransferObjectsListMapper: RoleTransferObjectViewListToRoleTransferObjectsListMapper
    ): RoleMappers = RoleMappers(
        roleEntityListToRolesListMapper,
        transferObjectEntityListToTransferObjectsListMapper,
        roleTransferObjectViewListToRoleTransferObjectsListMapper
    )

    // ------------------------------------------- CSV: -------------------------------------------
    // CongregationCsv
    @Singleton
    @Provides
    fun provideCongregationEntityToCongregationCsvMapper(): CongregationEntityToCongregationCsvMapper =
        CongregationEntityToCongregationCsvMapper()

    @Singleton
    @Provides
    fun provideCongregationEntityListToCongregationCsvListMapper(mapper: CongregationEntityToCongregationCsvMapper): CongregationEntityListToCongregationCsvListMapper =
        CongregationEntityListToCongregationCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationCsvToCongregationEntityMapper(): CongregationCsvToCongregationEntityMapper =
        CongregationCsvToCongregationEntityMapper()

    @Singleton
    @Provides
    fun provideCongregationCsvListToCongregationEntityListMapper(mapper: CongregationCsvToCongregationEntityMapper): CongregationCsvListToCongregationEntityListMapper =
        CongregationCsvListToCongregationEntityListMapper(mapper = mapper)

    // CongregationTotalCsv
    @Singleton
    @Provides
    fun provideCongregationTotalEntityToCongregationTotalCsvMapper(): CongregationTotalEntityToCongregationTotalCsvMapper =
        CongregationTotalEntityToCongregationTotalCsvMapper()

    @Singleton
    @Provides
    fun provideCongregationTotalEntityListToCongregationTotalCsvListMapper(mapper: CongregationTotalEntityToCongregationTotalCsvMapper): CongregationTotalEntityListToCongregationTotalCsvListMapper =
        CongregationTotalEntityListToCongregationTotalCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationTotalCsvToCongregationTotalEntityMapper(): CongregationTotalCsvToCongregationTotalEntityMapper =
        CongregationTotalCsvToCongregationTotalEntityMapper()

    @Singleton
    @Provides
    fun provideCongregationTotalCsvListToCongregationTotalEntityListMapper(mapper: CongregationTotalCsvToCongregationTotalEntityMapper): CongregationTotalCsvListToCongregationTotalEntityListMapper =
        CongregationTotalCsvListToCongregationTotalEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationCsvMappers(
        congregationEntityListToCongregationCsvListMapper: CongregationEntityListToCongregationCsvListMapper,
        congregationTotalEntityListToCongregationTotalCsvListMapper: CongregationTotalEntityListToCongregationTotalCsvListMapper,
        congregationCsvListToCongregationEntityListMapper: CongregationCsvListToCongregationEntityListMapper,
        congregationTotalCsvListToCongregationTotalEntityListMapper: CongregationTotalCsvListToCongregationTotalEntityListMapper
    ): CongregationCsvMappers = CongregationCsvMappers(
        congregationEntityListToCongregationCsvListMapper,
        congregationTotalEntityListToCongregationTotalCsvListMapper,
        congregationCsvListToCongregationEntityListMapper,
        congregationTotalCsvListToCongregationTotalEntityListMapper
    )

    // GroupCsv
    @Singleton
    @Provides
    fun provideGroupEntityToGroupCsvMapper(): GroupEntityToGroupCsvMapper =
        GroupEntityToGroupCsvMapper()

    @Singleton
    @Provides
    fun provideGroupEntityListToGroupCsvListMapper(mapper: GroupEntityToGroupCsvMapper): GroupEntityListToGroupCsvListMapper =
        GroupEntityListToGroupCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGroupCsvToGroupEntityMapper(): GroupCsvToGroupEntityMapper =
        GroupCsvToGroupEntityMapper()

    @Singleton
    @Provides
    fun provideGroupCsvListToGroupEntityListMapper(mapper: GroupCsvToGroupEntityMapper): GroupCsvListToGroupEntityListMapper =
        GroupCsvListToGroupEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGroupCsvMappers(
        groupEntityListToGroupCsvListMapper: GroupEntityListToGroupCsvListMapper,
        groupCsvListToGroupEntityListMapper: GroupCsvListToGroupEntityListMapper
    ): GroupCsvMappers = GroupCsvMappers(
        groupEntityListToGroupCsvListMapper,
        groupCsvListToGroupEntityListMapper
    )

    // TransferObjectCsv
    @Singleton
    @Provides
    fun provideTransferObjectEntityToTransferObjectCsvMapper(): TransferObjectEntityToTransferObjectCsvMapper =
        TransferObjectEntityToTransferObjectCsvMapper()

    @Singleton
    @Provides
    fun provideTransferObjectEntityListToTransferObjectCsvListMapper(mapper: TransferObjectEntityToTransferObjectCsvMapper): TransferObjectEntityListToTransferObjectCsvListMapper =
        TransferObjectEntityListToTransferObjectCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTransferObjectCsvToTransferObjectEntityMapper(): TransferObjectCsvToTransferObjectEntityMapper =
        TransferObjectCsvToTransferObjectEntityMapper()

    @Singleton
    @Provides
    fun provideTransferObjectCsvListToTransferObjectEntityListMapper(mapper: TransferObjectCsvToTransferObjectEntityMapper): TransferObjectCsvListToTransferObjectEntityListMapper =
        TransferObjectCsvListToTransferObjectEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTransferObjectCsvMappers(
        transferObjectEntityListToTransferObjectCsvListMapper: TransferObjectEntityListToTransferObjectCsvListMapper,
        transferObjectCsvListToTransferObjectEntityListMapper: TransferObjectCsvListToTransferObjectEntityListMapper
    ): TransferObjectCsvMappers = TransferObjectCsvMappers(
        transferObjectEntityListToTransferObjectCsvListMapper,
        transferObjectCsvListToTransferObjectEntityListMapper
    )

    // RoleCsv
    @Singleton
    @Provides
    fun provideRoleEntityToRoleCsvMapper(): RoleEntityToRoleCsvMapper = RoleEntityToRoleCsvMapper()

    @Singleton
    @Provides
    fun provideRoleEntityListToRoleCsvListMapper(mapper: RoleEntityToRoleCsvMapper): RoleEntityListToRoleCsvListMapper =
        RoleEntityListToRoleCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoleCsvToRoleEntityMapper(): RoleCsvToRoleEntityMapper = RoleCsvToRoleEntityMapper()

    @Singleton
    @Provides
    fun provideRoleCsvListToRoleEntityListMapper(mapper: RoleCsvToRoleEntityMapper): RoleCsvListToRoleEntityListMapper =
        RoleCsvListToRoleEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoleCsvMappers(
        roleEntityListToRoleCsvListMapper: RoleEntityListToRoleCsvListMapper,
        roleTransferObjectEntityListToRoleTransferObjectCsvListMapper: RoleTransferObjectEntityListToRoleTransferObjectCsvListMapper,
        roleCsvListToRoleEntityListMapper: RoleCsvListToRoleEntityListMapper,
        roleTransferObjectCsvListToRoleTransferObjectEntityListMapper: RoleTransferObjectCsvListToRoleTransferObjectEntityListMapper
    ): RoleCsvMappers = RoleCsvMappers(
        roleEntityListToRoleCsvListMapper,
        roleTransferObjectEntityListToRoleTransferObjectCsvListMapper,
        roleCsvListToRoleEntityListMapper,
        roleTransferObjectCsvListToRoleTransferObjectEntityListMapper
    )

    // MemberCongregationCrossRefCsv
    @Singleton
    @Provides
    fun provideMemberCongregationCrossRefEntityToMemberCongregationCrossRefCsvMapper(): MemberCongregationCrossRefEntityToMemberCongregationCrossRefCsvMapper =
        MemberCongregationCrossRefEntityToMemberCongregationCrossRefCsvMapper()

    @Singleton
    @Provides
    fun provideMemberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper(mapper: MemberCongregationCrossRefEntityToMemberCongregationCrossRefCsvMapper): MemberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper =
        MemberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberCongregationCrossRefCsvToMemberCongregationCrossRefEntityMapper(): MemberCongregationCrossRefCsvToMemberCongregationCrossRefEntityMapper =
        MemberCongregationCrossRefCsvToMemberCongregationCrossRefEntityMapper()

    @Singleton
    @Provides
    fun provideMemberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper(mapper: MemberCongregationCrossRefCsvToMemberCongregationCrossRefEntityMapper): MemberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper =
        MemberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper(mapper = mapper)

    // MemberMovementCsv
    @Singleton
    @Provides
    fun provideMemberMovementEntityToMemberMovementCsvMapper(): MemberMovementEntityToMemberMovementCsvMapper =
        MemberMovementEntityToMemberMovementCsvMapper()

    @Singleton
    @Provides
    fun provideMemberMovementEntityListToMemberMovementCsvListMapper(mapper: MemberMovementEntityToMemberMovementCsvMapper): MemberMovementEntityListToMemberMovementCsvListMapper =
        MemberMovementEntityListToMemberMovementCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberMovementCsvToMemberMovementEntityMapper(): MemberMovementCsvToMemberMovementEntityMapper =
        MemberMovementCsvToMemberMovementEntityMapper()

    @Singleton
    @Provides
    fun provideMemberMovementCsvListToMemberMovementEntityListMapper(mapper: MemberMovementCsvToMemberMovementEntityMapper): MemberMovementCsvListToMemberMovementEntityListMapper =
        MemberMovementCsvListToMemberMovementEntityListMapper(mapper = mapper)

    // MemberRoleCsv
    @Singleton
    @Provides
    fun provideMemberRoleEntityToMemberRoleCsvMapper(): MemberRoleEntityToMemberRoleCsvMapper =
        MemberRoleEntityToMemberRoleCsvMapper()

    @Singleton
    @Provides
    fun provideMemberRoleEntityListToMemberRoleCsvListMapper(mapper: MemberRoleEntityToMemberRoleCsvMapper): MemberRoleEntityListToMemberRoleCsvListMapper =
        MemberRoleEntityListToMemberRoleCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRoleCsvToMemberRoleEntityMapper(): MemberRoleCsvToMemberRoleEntityMapper =
        MemberRoleCsvToMemberRoleEntityMapper()

    @Singleton
    @Provides
    fun provideMemberRoleCsvListToMemberRoleEntityListMapper(mapper: MemberRoleCsvToMemberRoleEntityMapper): MemberRoleCsvListToMemberRoleEntityListMapper =
        MemberRoleCsvListToMemberRoleEntityListMapper(mapper = mapper)

    // RoleTransferObjectCsv
    @Singleton
    @Provides
    fun provideRoleTransferObjectEntityToRoleTransferObjectCsvMapper(): RoleTransferObjectEntityToRoleTransferObjectCsvMapper =
        RoleTransferObjectEntityToRoleTransferObjectCsvMapper()

    @Singleton
    @Provides
    fun provideRoleTransferObjectEntityListToRoleTransferObjectCsvListMapper(mapper: RoleTransferObjectEntityToRoleTransferObjectCsvMapper): RoleTransferObjectEntityListToRoleTransferObjectCsvListMapper =
        RoleTransferObjectEntityListToRoleTransferObjectCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoleTransferObjectCsvToRoleTransferObjectEntityMapper(): RoleTransferObjectCsvToRoleTransferObjectEntityMapper =
        RoleTransferObjectCsvToRoleTransferObjectEntityMapper()

    @Singleton
    @Provides
    fun provideRoleTransferObjectCsvListToRoleTransferObjectEntityListMapper(mapper: RoleTransferObjectCsvToRoleTransferObjectEntityMapper): RoleTransferObjectCsvListToRoleTransferObjectEntityListMapper =
        RoleTransferObjectCsvListToRoleTransferObjectEntityListMapper(mapper = mapper)

    // MemberCsv
    @Singleton
    @Provides
    fun provideMemberEntityToMemberCsvMapper(): MemberEntityToMemberCsvMapper =
        MemberEntityToMemberCsvMapper()

    @Singleton
    @Provides
    fun provideMemberEntityListToMemberCsvListMapper(mapper: MemberEntityToMemberCsvMapper): MemberEntityListToMemberCsvListMapper =
        MemberEntityListToMemberCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberCsvToMemberEntityMapper(): MemberCsvToMemberEntityMapper =
        MemberCsvToMemberEntityMapper()

    @Singleton
    @Provides
    fun provideMemberCsvListToMemberEntityListMapper(mapper: MemberCsvToMemberEntityMapper): MemberCsvListToMemberEntityListMapper =
        MemberCsvListToMemberEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberCsvMappers(
        memberEntityListToMemberCsvListMapper: MemberEntityListToMemberCsvListMapper,
        memberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper: MemberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper,
        memberMovementEntityListToMemberMovementCsvListMapper: MemberMovementEntityListToMemberMovementCsvListMapper,
        memberRoleEntityListToMemberRoleCsvListMapper: MemberRoleEntityListToMemberRoleCsvListMapper,
        memberCsvListToMemberEntityListMapper: MemberCsvListToMemberEntityListMapper,
        memberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper: MemberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper,
        memberMovementCsvListToMemberMovementEntityListMapper: MemberMovementCsvListToMemberMovementEntityListMapper,
        memberRoleCsvListToMemberRoleEntityListMapper: MemberRoleCsvListToMemberRoleEntityListMapper
    ): MemberCsvMappers = MemberCsvMappers(
        memberEntityListToMemberCsvListMapper,
        memberCongregationCrossRefEntityListToMemberCongregationCrossRefCsvListMapper,
        memberMovementEntityListToMemberMovementCsvListMapper,
        memberRoleEntityListToMemberRoleCsvListMapper,
        memberCsvListToMemberEntityListMapper,
        memberCongregationCrossRefCsvListToMemberCongregationCrossRefEntityListMapper,
        memberMovementCsvListToMemberMovementEntityListMapper,
        memberRoleCsvListToMemberRoleEntityListMapper
    )
}