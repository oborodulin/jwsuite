package com.oborodulin.jwsuite.data_congregation.di

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
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewToMemberRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.RoleEntityListToRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.RoleEntityToRoleMapper
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
    fun provideCongregationViewToCongregationMapper(
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper
    ): CongregationViewToCongregationMapper = CongregationViewToCongregationMapper(
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper
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
    fun provideMemberRoleViewToMemberRoleMapper(mapper: RoleEntityToRoleMapper): MemberRoleViewToMemberRoleMapper =
        MemberRoleViewToMemberRoleMapper(mapper = mapper)

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
        congregationMapper: CongregationViewToCongregationMapper,
        groupMapper: GroupViewToGroupMapper,
        movementMapper: MemberMovementEntityToMemberMovementMapper
    ): MemberViewToMemberMapper = MemberViewToMemberMapper(
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
        memberRoleViewListToMemberRolesListMapper: MemberRoleViewListToMemberRolesListMapper,
        memberRoleToMemberRoleEntityMapper: MemberRoleToMemberRoleEntityMapper,
        memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper
    ): MemberMappers = MemberMappers(
        memberViewListToMembersListMapper,
        memberViewToMemberMapper,
        membersListToMemberEntityListMapper,
        memberToMemberEntityMapper,
        memberToMemberCongregationCrossRefEntityMapper,
        roleEntityListToRolesListMapper,
        memberRoleViewListToMemberRolesListMapper,
        memberRoleToMemberRoleEntityMapper,
        memberToMemberMovementEntityMapper
    )
}