package com.oborodulin.jwsuite.data_congregation.di

import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.CongregationsRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.GroupsRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.MembersRepositoryImpl
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalCongregationDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalGroupDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
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
        mappers: CongregationMappers
    ): CongregationsRepository =
        CongregationsRepositoryImpl(localCongregationDataSource, mappers)

    @Singleton
    @Provides
    fun provideGroupsRepository(
        localGroupDataSource: LocalGroupDataSource, mappers: GroupMappers
    ): GroupsRepository = GroupsRepositoryImpl(localGroupDataSource, mappers)

    @Singleton
    @Provides
    fun provideMembersRepository(
        localMemberDataSource: LocalMemberDataSource, memberMappers: MemberMappers,
        transferObjectMappers: TransferObjectMappers
    ): MembersRepository =
        MembersRepositoryImpl(localMemberDataSource, memberMappers, transferObjectMappers)
}