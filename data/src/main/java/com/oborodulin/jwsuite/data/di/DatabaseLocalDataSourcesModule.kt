package com.oborodulin.jwsuite.data.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.DatabaseDao
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalDatabaseDataSource
import com.oborodulin.jwsuite.data.sources.local.LocalDatabaseDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseLocalDataSourcesModule {
    @Singleton
    @Provides
    fun provideDatabaseDataSource(
        databaseDao: DatabaseDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalDatabaseDataSource = LocalDatabaseDataSourceImpl(databaseDao, dispatcher)
}