package com.oborodulin.jwsuite.data_congregation.di

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group.GroupCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.MemberCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.RoleCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.CongregationsRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.GroupsRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.MembersRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.RolesRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.TransferRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalCongregationDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalGroupDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalRoleDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalTransferDataSource
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.RolesRepository
import com.oborodulin.jwsuite.domain.repositories.TransferRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CongregationRepositoriesModule {
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
}