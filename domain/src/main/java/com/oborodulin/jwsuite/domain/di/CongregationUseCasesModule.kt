package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.RolesRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.congregation.DeleteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationsUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationTotalsUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.MakeFavoriteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.SaveCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.group.DeleteGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupsUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GetNextGroupNumUseCase
import com.oborodulin.jwsuite.domain.usecases.group.SaveGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.member.DeleteMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersWithUsernameUseCase
import com.oborodulin.jwsuite.domain.usecases.member.SaveMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.DeleteMemberRoleUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRoleUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRolesUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.SaveMemberRoleUseCase
import com.oborodulin.jwsuite.domain.usecases.role.GetRolesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CongregationUseCasesModule {
    // Congregation:
    @Singleton
    @Provides
    fun provideGetCongregationUseCase(
        configuration: UseCase.Configuration, congregationsRepository: CongregationsRepository
    ): GetCongregationUseCase = GetCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideGetCongregationsUseCase(
        configuration: UseCase.Configuration, congregationsRepository: CongregationsRepository
    ): GetCongregationsUseCase = GetCongregationsUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideGetFavoriteCongregationUseCase(
        configuration: UseCase.Configuration, congregationsRepository: CongregationsRepository
    ): GetFavoriteCongregationUseCase =
        GetFavoriteCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideMakeFavoriteCongregationUseCase(
        configuration: UseCase.Configuration, congregationsRepository: CongregationsRepository
    ): MakeFavoriteCongregationUseCase =
        MakeFavoriteCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideGetFavoriteCongregationTotalsUseCase(
        configuration: UseCase.Configuration, congregationsRepository: CongregationsRepository
    ): GetFavoriteCongregationTotalsUseCase =
        GetFavoriteCongregationTotalsUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideDeleteCongregationUseCase(
        configuration: UseCase.Configuration, congregationsRepository: CongregationsRepository
    ): DeleteCongregationUseCase = DeleteCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideSaveCongregationUseCase(
        configuration: UseCase.Configuration, congregationsRepository: CongregationsRepository
    ): SaveCongregationUseCase = SaveCongregationUseCase(configuration, congregationsRepository)

    // Group:
    @Singleton
    @Provides
    fun provideGetGroupUseCase(
        configuration: UseCase.Configuration, groupsRepository: GroupsRepository
    ): GetGroupUseCase = GetGroupUseCase(configuration, groupsRepository)

    @Singleton
    @Provides
    fun provideGetGroupsUseCase(
        configuration: UseCase.Configuration, groupsRepository: GroupsRepository
    ): GetGroupsUseCase = GetGroupsUseCase(configuration, groupsRepository)

    @Singleton
    @Provides
    fun provideGetNextGroupNumUseCase(
        configuration: UseCase.Configuration, groupsRepository: GroupsRepository
    ): GetNextGroupNumUseCase = GetNextGroupNumUseCase(configuration, groupsRepository)

    @Singleton
    @Provides
    fun provideDeleteGroupUseCase(
        configuration: UseCase.Configuration, groupsRepository: GroupsRepository
    ): DeleteGroupUseCase = DeleteGroupUseCase(configuration, groupsRepository)

    @Singleton
    @Provides
    fun provideSaveGroupUseCase(
        configuration: UseCase.Configuration, groupsRepository: GroupsRepository
    ): SaveGroupUseCase = SaveGroupUseCase(configuration, groupsRepository)

    // Member:
    @Singleton
    @Provides
    fun provideGetMemberUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): GetMemberUseCase = GetMemberUseCase(configuration, membersRepository)

    @Singleton
    @Provides
    fun provideGetMembersUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): GetMembersUseCase = GetMembersUseCase(configuration, membersRepository)

    @Singleton
    @Provides
    fun provideGetMembersWithUsernameUseCase(
        configuration: UseCase.Configuration,
        membersRepository: MembersRepository, sessionManagerRepository: SessionManagerRepository
    ): GetMembersWithUsernameUseCase =
        GetMembersWithUsernameUseCase(configuration, membersRepository, sessionManagerRepository)

    @Singleton
    @Provides
    fun provideDeleteMemberUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): DeleteMemberUseCase = DeleteMemberUseCase(configuration, membersRepository)

    @Singleton
    @Provides
    fun provideSaveMemberUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): SaveMemberUseCase = SaveMemberUseCase(configuration, membersRepository)

    // Member Roles:
    @Singleton
    @Provides
    fun provideGetMemberRoleUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): GetMemberRoleUseCase = GetMemberRoleUseCase(configuration, membersRepository)

    @Singleton
    @Provides
    fun provideGetMemberRolesUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): GetMemberRolesUseCase = GetMemberRolesUseCase(configuration, membersRepository)

    @Singleton
    @Provides
    fun provideDeleteMemberRoleUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): DeleteMemberRoleUseCase = DeleteMemberRoleUseCase(configuration, membersRepository)

    @Singleton
    @Provides
    fun provideSaveMemberRoleUseCase(
        configuration: UseCase.Configuration, membersRepository: MembersRepository
    ): SaveMemberRoleUseCase = SaveMemberRoleUseCase(configuration, membersRepository)

    // Roles:
    @Singleton
    @Provides
    fun provideGetRolesUseCase(
        configuration: UseCase.Configuration,
        rolesRepository: RolesRepository, membersRepository: MembersRepository
    ): GetRolesUseCase = GetRolesUseCase(configuration, rolesRepository, membersRepository)
}