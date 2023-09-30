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
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberToMemberMovementEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewListToMembersListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MembersListToMemberEntityListMapper
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
    @Singleton
    @Provides
    fun provideMemberViewToMemberMapper(mapper: GroupViewToGroupMapper): MemberViewToMemberMapper =
        MemberViewToMemberMapper(groupMapper = mapper)

    @Singleton
    @Provides
    fun provideMMemberViewListToMembersListMapper(mapper: MemberViewToMemberMapper): MemberViewListToMembersListMapper =
        MemberViewListToMembersListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberToMemberEntityMapper(): MemberToMemberEntityMapper =
        MemberToMemberEntityMapper()

    @Singleton
    @Provides
    fun provideMemberToMemberMovementEntityMapper(): MemberToMemberMovementEntityMapper =
        MemberToMemberMovementEntityMapper()

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
        memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper
    ): MemberMappers = MemberMappers(
        memberViewListToMembersListMapper,
        memberViewToMemberMapper,
        membersListToMemberEntityListMapper,
        memberToMemberEntityMapper,
        memberToMemberMovementEntityMapper
    )
}