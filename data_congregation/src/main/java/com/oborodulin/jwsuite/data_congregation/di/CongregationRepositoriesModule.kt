package com.oborodulin.jwsuite.data_congregation.di

import com.oborodulin.jwsuite.data_congregation.local.db.repositories.CongregationsRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.GroupsRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.MembersRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.RolesRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.TransferRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.RolesRepository
import com.oborodulin.jwsuite.domain.repositories.TransferRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object CongregationRepositoriesModule {
abstract class CongregationRepositoriesModule {
    @Binds
    abstract fun bindCongregationsRepository(repositoryImpl: CongregationsRepositoryImpl): CongregationsRepository

    @Binds
    abstract fun bindGroupsRepository(repositoryImpl: GroupsRepositoryImpl): GroupsRepository

    @Binds
    abstract fun bindMembersRepository(repositoryImpl: MembersRepositoryImpl): MembersRepository

    @Binds
    abstract fun bindRolesRepository(repositoryImpl: RolesRepositoryImpl): RolesRepository

    @Binds
    abstract fun bindTransferRepository(repositoryImpl: TransferRepositoryImpl): TransferRepository
    /*
        @Singleton //@ViewModelScoped
        @Provides
        fun provideCongregationsRepository(
            localCongregationDataSource: LocalCongregationDataSource,
            domainMappers: CongregationMappers, csvMappers: CongregationCsvMappers
        ): CongregationsRepository =
            CongregationsRepositoryImpl(localCongregationDataSource, domainMappers, csvMappers)

        @Singleton
        @Provides
        fun provideGroupsRepository(
            localGroupDataSource: LocalGroupDataSource,
            domainMappers: GroupMappers, csvMappers: GroupCsvMappers
        ): GroupsRepository = GroupsRepositoryImpl(localGroupDataSource, domainMappers, csvMappers)

        @Singleton
        @Provides
        fun provideMembersRepository(
            localMemberDataSource: LocalMemberDataSource,
            memberMappers: MemberMappers,
            roleMappers: RoleMappers,
            csvMemberMappers: MemberCsvMappers
        ): MembersRepository = MembersRepositoryImpl(
            localMemberDataSource,
            memberMappers,
            roleMappers,
            csvMemberMappers
        )

        @Singleton
        @Provides
        fun provideRolesRepository(
            localRoleDataSource: LocalRoleDataSource,
            domainMappers: RoleMappers, csvMappers: RoleCsvMappers
        ): RolesRepository = RolesRepositoryImpl(localRoleDataSource, domainMappers, csvMappers)

        @Singleton
        @Provides
        fun provideTransferRepository(
            localTransferDataSource: LocalTransferDataSource,
            domainMappers: TransferObjectMappers, csvMappers: TransferObjectCsvMappers
        ): TransferRepository =
            TransferRepositoryImpl(localTransferDataSource, domainMappers, csvMappers)
     */
}