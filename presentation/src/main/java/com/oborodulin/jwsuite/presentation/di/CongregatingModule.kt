package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.congregation.CongregationUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.DeleteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationsUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.MakeFavoriteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.SaveCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.group.DeleteGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupsUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GroupUseCases
import com.oborodulin.jwsuite.domain.usecases.group.SaveGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.member.DeleteMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.domain.usecases.member.SaveMemberUseCase
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.CongregationConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.CongregationsListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.GroupConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.GroupsListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.MemberConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.MembersListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationUiToCongregationMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationsListToCongregationsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupToGroupUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupToGroupsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupUiToGroupMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupsListToGroupsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MemberToMemberUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MemberToMembersListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MemberUiToMemberMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MembersListToMembersListItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CongregatingModule {
    // MAPPERS:
    // Congregation:
    @Singleton
    @Provides
    fun provideCongregationToCongregationUiMapper(mapper: LocalityToLocalityUiMapper): CongregationToCongregationUiMapper =
        CongregationToCongregationUiMapper(localityMapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationUiToCongregationMapper(mapper: LocalityUiToLocalityMapper): CongregationUiToCongregationMapper =
        CongregationUiToCongregationMapper(localityUiMapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationToCongregationsListItemMapper(mapper: LocalityToLocalityUiMapper): CongregationToCongregationsListItemMapper =
        CongregationToCongregationsListItemMapper(localityMapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationsListToCongregationsListItemMapper(mapper: CongregationToCongregationsListItemMapper): CongregationsListToCongregationsListItemMapper =
        CongregationsListToCongregationsListItemMapper(mapper = mapper)

    // Group:
    @Singleton
    @Provides
    fun provideGroupToGroupUiMapper(mapper: CongregationToCongregationUiMapper): GroupToGroupUiMapper =
        GroupToGroupUiMapper(congregationMapper = mapper)

    @Singleton
    @Provides
    fun provideGroupUiToGroupMapper(mapper: CongregationUiToCongregationMapper): GroupUiToGroupMapper =
        GroupUiToGroupMapper(congregationUiMapper = mapper)

    @Singleton
    @Provides
    fun provideGroupToGroupsListItemMapper(): GroupToGroupsListItemMapper =
        GroupToGroupsListItemMapper()

    @Singleton
    @Provides
    fun provideGroupsListToGroupsListItemMapper(mapper: GroupToGroupsListItemMapper): GroupsListToGroupsListItemMapper =
        GroupsListToGroupsListItemMapper(mapper = mapper)

    // Member:
    @Singleton
    @Provides
    fun provideMemberToMemberUiMapper(mapper: GroupToGroupUiMapper): MemberToMemberUiMapper =
        MemberToMemberUiMapper(groupMapper = mapper)

    @Singleton
    @Provides
    fun provideMemberUiToMemberMapper(mapper: GroupUiToGroupMapper): MemberUiToMemberMapper =
        MemberUiToMemberMapper(groupUiMapper = mapper)

    @Singleton
    @Provides
    fun provideMemberToMembersListItemMapper(mapper: GroupToGroupUiMapper): MemberToMembersListItemMapper =
        MemberToMembersListItemMapper(groupMapper = mapper)

    @Singleton
    @Provides
    fun provideMembersListToMembersListItemMapper(mapper: MemberToMembersListItemMapper): MembersListToMembersListItemMapper =
        MembersListToMembersListItemMapper(mapper = mapper)

    // CONVERTERS:
    // Congregation:
    @Singleton
    @Provides
    fun provideCongregationsListConverter(mapper: CongregationsListToCongregationsListItemMapper): CongregationsListConverter =
        CongregationsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationConverter(mapper: CongregationToCongregationUiMapper): CongregationConverter =
        CongregationConverter(mapper = mapper)

    // Group:
    @Singleton
    @Provides
    fun provideGroupsListConverter(mapper: GroupsListToGroupsListItemMapper): GroupsListConverter =
        GroupsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideGroupConverter(mapper: GroupToGroupUiMapper): GroupConverter =
        GroupConverter(mapper = mapper)

    // Group:
    @Singleton
    @Provides
    fun provideMembersListConverter(mapper: MembersListToMembersListItemMapper): MembersListConverter =
        MembersListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberConverter(mapper: MemberToMemberUiMapper): MemberConverter =
        MemberConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideCongregationUseCases(
        getCongregationsUseCase: GetCongregationsUseCase,
        getCongregationUseCase: GetCongregationUseCase,
        saveCongregationUseCase: SaveCongregationUseCase,
        deleteCongregationUseCase: DeleteCongregationUseCase,
        makeFavoriteCongregationUseCase: MakeFavoriteCongregationUseCase
    ): CongregationUseCases = CongregationUseCases(
        getCongregationsUseCase,
        getCongregationUseCase,
        saveCongregationUseCase,
        deleteCongregationUseCase,
        makeFavoriteCongregationUseCase
    )

    @Singleton
    @Provides
    fun provideGroupUseCases(
        getGroupsUseCase: GetGroupsUseCase,
        getGroupUseCase: GetGroupUseCase,
        saveGroupUseCase: SaveGroupUseCase,
        deleteGroupUseCase: DeleteGroupUseCase
    ): GroupUseCases = GroupUseCases(
        getGroupsUseCase,
        getGroupUseCase,
        saveGroupUseCase,
        deleteGroupUseCase
    )

    @Singleton
    @Provides
    fun provideMemberUseCases(
        getMembersUseCase: GetMembersUseCase,
        getMemberUseCase: GetMemberUseCase,
        saveMemberUseCase: SaveMemberUseCase,
        deleteMemberUseCase: DeleteMemberUseCase
    ): MemberUseCases = MemberUseCases(
        getMembersUseCase,
        getMemberUseCase,
        saveMemberUseCase,
        deleteMemberUseCase
    )
}