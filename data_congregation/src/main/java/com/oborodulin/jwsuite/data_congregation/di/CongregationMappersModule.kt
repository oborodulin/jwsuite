package com.oborodulin.jwsuite.data_congregation.di

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
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberToMemberCongregationCrossRefEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberMovementEntityToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberToMemberMovementEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleToMemberRoleEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToMemberRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewToMemberRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewToRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.RoleEntityListToRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.RoleEntityToRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.MemberRoleTransferObjectToRoleTransferObjectEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.MemberRoleTransferObjectViewListToTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.MemberRoleTransferObjectViewToTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.RoleTransferObjectViewListToRoleTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.RoleTransferObjectViewToRoleTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectEntityListToTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectEntityToTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
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
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        congregationMapper: CongregationEntityToCongregationMapper
    ): CongregationViewToCongregationMapper = CongregationViewToCongregationMapper(
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
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
    // Member Role:
    @Singleton
    @Provides
    fun provideRoleEntityToRoleMapper(): RoleEntityToRoleMapper = RoleEntityToRoleMapper()

    @Singleton
    @Provides
    fun provideRoleEntityListToRolesListMapper(mapper: RoleEntityToRoleMapper): RoleEntityListToRolesListMapper =
        RoleEntityListToRolesListMapper(mapper = mapper)

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

    // Member Movement:
    @Singleton
    @Provides
    fun provideMemberToMemberMovementEntityMapper(): MemberToMemberMovementEntityMapper =
        MemberToMemberMovementEntityMapper()

    @Singleton
    @Provides
    fun provideMemberMovementEntityToMemberMovementMapper(): MemberMovementEntityToMemberMovementMapper =
        MemberMovementEntityToMemberMovementMapper()

    // Member:
    @Singleton
    @Provides
    fun provideMemberViewToMemberMapper(
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        congregationMapper: CongregationEntityToCongregationMapper,
        groupMapper: GroupViewToGroupMapper,
        movementMapper: MemberMovementEntityToMemberMovementMapper
    ): MemberViewToMemberMapper = MemberViewToMemberMapper(
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        congregationMapper = congregationMapper,
        groupMapper = groupMapper,
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
    fun provideMemberToMemberCongregationCrossRefEntityMapper(): MemberToMemberCongregationCrossRefEntityMapper =
        MemberToMemberCongregationCrossRefEntityMapper()

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
        roleEntityListToRolesListMapper: RoleEntityListToRolesListMapper,
        memberRoleViewToMemberRoleMapper: MemberRoleViewToMemberRoleMapper,
        memberRoleViewListToMemberRolesListMapper: MemberRoleViewListToMemberRolesListMapper,
        memberRoleViewListToRolesListMapper: MemberRoleViewListToRolesListMapper,
        memberRoleToMemberRoleEntityMapper: MemberRoleToMemberRoleEntityMapper,
        memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper
    ): MemberMappers = MemberMappers(
        memberViewListToMembersListMapper,
        memberViewToMemberMapper,
        membersListToMemberEntityListMapper,
        memberToMemberEntityMapper,
        memberToMemberCongregationCrossRefEntityMapper,
        roleEntityListToRolesListMapper,
        memberRoleViewToMemberRoleMapper,
        memberRoleViewListToMemberRolesListMapper,
        memberRoleViewListToRolesListMapper,
        memberRoleToMemberRoleEntityMapper,
        memberToMemberMovementEntityMapper
    )

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
        transferObjectEntityListToTransferObjectsListMapper: TransferObjectEntityListToTransferObjectsListMapper,
        roleTransferObjectViewListToRoleTransferObjectsListMapper: RoleTransferObjectViewListToRoleTransferObjectsListMapper,
        memberRoleTransferObjectToRoleTransferObjectEntityMapper: MemberRoleTransferObjectToRoleTransferObjectEntityMapper,
        memberRoleTransferObjectViewToTransferObjectMapper: MemberRoleTransferObjectViewToTransferObjectMapper,
        memberRoleTransferObjectViewListToTransferObjectsListMapper: MemberRoleTransferObjectViewListToTransferObjectsListMapper,
        memberRoleTransferObjectViewToMemberRoleTransferObjectMapper: MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper,
        memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper: MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
    ): TransferObjectMappers = TransferObjectMappers(
        transferObjectEntityToTransferObjectMapper,
        transferObjectEntityListToTransferObjectsListMapper,
        roleTransferObjectViewListToRoleTransferObjectsListMapper,
        memberRoleTransferObjectToRoleTransferObjectEntityMapper,
        memberRoleTransferObjectViewToTransferObjectMapper,
        memberRoleTransferObjectViewListToTransferObjectsListMapper,
        memberRoleTransferObjectViewToMemberRoleTransferObjectMapper,
        memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
    )
}