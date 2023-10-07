package com.oborodulin.jwsuite.data.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.datastore.repositories.SessionManagerRepositoryImpl
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionManagerRepositoriesModule {
    @Singleton
    @Provides
    fun provideSessionManagerRepository(
        localSessionManagerDataSource: LocalSessionManagerDataSource,
        localMemberDataSource: LocalMemberDataSource,
        mappers: MemberMappers,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): SessionManagerRepository = SessionManagerRepositoryImpl(
        localSessionManagerDataSource,
        localMemberDataSource,
        mappers,
        dispatcher
    )
}