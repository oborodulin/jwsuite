package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.repositories.DatabaseRepositoryImpl
import com.oborodulin.jwsuite.data.local.db.repositories.EventsRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.EventsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object DatabaseRepositoriesModule {
abstract class DatabaseRepositoriesModule {
    @Binds
    abstract fun bindDatabaseRepository(repositoryImpl: DatabaseRepositoryImpl): DatabaseRepository

    @Binds
    abstract fun bindEventsRepository(repositoryImpl: EventsRepositoryImpl): EventsRepository
    /*
    @Provides
@Singleton
fun provideDatabaseRepository(
    @ApplicationContext ctx: Context,
    localDatabaseDataSource: LocalDatabaseDataSource,
    databaseVersion: DatabaseVersion
): DatabaseRepository = DatabaseRepositoryImpl(ctx, localDatabaseDataSource, databaseVersion)

@Provides
@Singleton
fun provideEventsRepository(
    localEventDataSource: LocalEventDataSource,
    domainMappers: EventMappers,
    csvMappers: EventCsvMappers
): EventsRepository = EventsRepositoryImpl(localEventDataSource, domainMappers, csvMappers)
     */
}