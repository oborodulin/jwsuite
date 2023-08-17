package com.oborodulin.jwsuite.data_congregation.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.local.LocalCongregationDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.local.LocalGroupDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.local.LocalMemberDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.sources.local.LocalCongregationDataSourceImpl
import com.oborodulin.jwsuite.data_congregation.local.db.sources.local.LocalGroupDataSourceImpl
import com.oborodulin.jwsuite.data_congregation.local.db.sources.local.LocalMemberDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CongregationLocalDataSourcesModule {
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
        memberDao: MemberDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalMemberDataSource = LocalMemberDataSourceImpl(memberDao, dispatcher)
}