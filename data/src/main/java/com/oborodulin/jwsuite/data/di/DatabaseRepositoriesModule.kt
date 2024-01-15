package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.repositories.DatabaseRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseRepositoriesModule {
    @Provides
    @Singleton
    fun provideDatabaseRepository(): DatabaseRepository = DatabaseRepositoryImpl()
}