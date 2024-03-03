package com.oborodulin.jwsuite.data.di

import android.content.Context
import com.oborodulin.jwsuite.data.local.db.DatabaseVersion
import com.oborodulin.jwsuite.data.local.db.repositories.DatabaseRepositoryImpl
import com.oborodulin.jwsuite.data.local.db.repositories.EventsRepositoryImpl
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalDatabaseDataSource
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.EventsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//object DatabaseRepositoriesModule {
interface DatabaseRepositoriesModule {
    //@Binds abstract fun bindDatabaseRepository(repositoryImpl: DatabaseRepositoryImpl): DatabaseRepository

    @Binds
    fun bindEventsRepository(repositoryImpl: EventsRepositoryImpl): EventsRepository

    // https://stackoverflow.com/questions/46618763/dagger2-how-to-use-provides-and-binds-in-same-module
    companion object {
        @Provides
        @Singleton
        fun provideDatabaseRepository(
            @ApplicationContext ctx: Context,
            localDatabaseDataSource: LocalDatabaseDataSource,
            databaseVersion: DatabaseVersion
        ): DatabaseRepository =
            DatabaseRepositoryImpl(ctx, localDatabaseDataSource, databaseVersion)
    }
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