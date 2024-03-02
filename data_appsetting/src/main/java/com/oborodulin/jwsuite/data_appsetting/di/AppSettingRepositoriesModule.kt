package com.oborodulin.jwsuite.data_appsetting.di

import com.oborodulin.jwsuite.data_appsetting.local.db.repositories.AppSettingsRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object AppSettingRepositoriesModule {
abstract class AppSettingRepositoriesModule {
    @Binds
    abstract fun bindAppSettingsRepository(repositoryImpl: AppSettingsRepositoryImpl): AppSettingsRepository
    /*
        @Singleton //@ViewModelScoped
        @Provides
        fun provideAppSettingsRepository(
            localAppSettingDataSource: LocalAppSettingDataSource,
            domainMappers: AppSettingMappers, csvMappers: AppSettingCsvMappers
        ): AppSettingsRepository =
            AppSettingsRepositoryImpl(localAppSettingDataSource, domainMappers, csvMappers)
     */
}