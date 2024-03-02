package com.oborodulin.jwsuite.data_congregation.di

import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalCongregationDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalGroupDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalRoleDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalTransferDataSource
import com.oborodulin.jwsuite.data_congregation.sources.local.LocalCongregationDataSourceImpl
import com.oborodulin.jwsuite.data_congregation.sources.local.LocalGroupDataSourceImpl
import com.oborodulin.jwsuite.data_congregation.sources.local.LocalMemberDataSourceImpl
import com.oborodulin.jwsuite.data_congregation.sources.local.LocalRoleDataSourceImpl
import com.oborodulin.jwsuite.data_congregation.sources.local.LocalTransferDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object CongregationLocalDataSourcesModule {
abstract class CongregationLocalDataSourcesModule {
    @Binds
    abstract fun bindLocalCongregationDataSource(dataSourceImpl: LocalCongregationDataSourceImpl): LocalCongregationDataSource

    @Binds
    abstract fun bindLocalGroupDataSource(dataSourceImpl: LocalGroupDataSourceImpl): LocalGroupDataSource

    @Binds
    abstract fun bindLocalMemberDataSource(dataSourceImpl: LocalMemberDataSourceImpl): LocalMemberDataSource

    @Binds
    abstract fun bindLocalRoleDataSource(dataSourceImpl: LocalRoleDataSourceImpl): LocalRoleDataSource

    @Binds
    abstract fun bindLocalTransferDataSource(dataSourceImpl: LocalTransferDataSourceImpl): LocalTransferDataSource
    /*
        @Singleton
        @Provides
        fun provideLocalCongregationDataSource(
            congregationDao: CongregationDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalCongregationDataSource = LocalCongregationDataSourceImpl(congregationDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalGroupDataSource(
            groupDao: GroupDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalGroupDataSource = LocalGroupDataSourceImpl(groupDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalMemberDataSource(
            memberDao: MemberDao,
            @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalMemberDataSource = LocalMemberDataSourceImpl(memberDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalRoleDataSource(
            roleDao: RoleDao,
            @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalRoleDataSource = LocalRoleDataSourceImpl(roleDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalTransferDataSource(
            transferDao: TransferDao,
            @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalTransferDataSource = LocalTransferDataSourceImpl(transferDao, dispatcher)
     */
}