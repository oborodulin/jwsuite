package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.datastore.repositories.SessionManagerRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object SessionManagerRepositoriesModule {
abstract class SessionManagerRepositoriesModule {
    @Binds
    abstract fun bindSessionManagerRepository(repositoryImpl: SessionManagerRepositoryImpl): SessionManagerRepository
    /*
        @Singleton
        @Provides
        fun provideSessionManagerRepository(
            localSessionManagerDataSource: LocalSessionManagerDataSource//,
            //localMemberDataSource: LocalMemberDataSource,
            //mappers: MemberMappers,
            //@IoDispatcher dispatcher: CoroutineDispatcher
        ): SessionManagerRepository = SessionManagerRepositoryImpl(
            localSessionManagerDataSource//,
            //localMemberDataSource,
            //mappers,
            //dispatcher
        )
     */
}