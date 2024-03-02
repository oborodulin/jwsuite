package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalDatabaseDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalEventDataSource
import com.oborodulin.jwsuite.data.sources.local.LocalDatabaseDataSourceImpl
import com.oborodulin.jwsuite.data.sources.local.LocalEventDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object DatabaseLocalDataSourcesModule {
abstract class DatabaseLocalDataSourcesModule {
    @Binds
    abstract fun bindLocalDatabaseDataSource(dataSourceImpl: LocalDatabaseDataSourceImpl): LocalDatabaseDataSource

    @Binds
    abstract fun bindLocalEventDataSource(dataSourceImpl: LocalEventDataSourceImpl): LocalEventDataSource
    /*
    @Singleton
    @Provides
    fun provideDatabaseDataSource(
        databaseDao: DatabaseDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalDatabaseDataSource = LocalDatabaseDataSourceImpl(databaseDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalEventDataSource(
        eventDao: EventDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalEventDataSource = LocalEventDataSourceImpl(eventDao, dispatcher)
     */
}