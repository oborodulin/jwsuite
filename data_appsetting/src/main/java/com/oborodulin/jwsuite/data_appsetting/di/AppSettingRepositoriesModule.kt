package com.oborodulin.jwsuite.data_appsetting.di

import com.oborodulin.jwsuite.data_appsetting.local.db.mappers.AppSettingMappers
import com.oborodulin.jwsuite.data_appsetting.local.db.repositories.AppSettingsRepositoryImpl
import com.oborodulin.jwsuite.data_appsetting.local.db.repositories.sources.LocalAppSettingDataSource
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppSettingRepositoriesModule {
    @Singleton //@ViewModelScoped
    @Provides
    fun provideAppSettingsRepository(
        localAppSettingDataSource: LocalAppSettingDataSource, mappers: AppSettingMappers
    ): AppSettingsRepository = AppSettingsRepositoryImpl(
        localAppSettingDataSource,
        mappers
    )
}