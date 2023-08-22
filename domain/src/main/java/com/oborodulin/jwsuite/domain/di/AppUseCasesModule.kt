package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.appsetting.CheckpointUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.DeleteAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppUseCasesModule {
    // AppSetting:
    @Singleton
    @Provides
    fun provideGetAppSettingUseCase(
        configuration: UseCase.Configuration, appSettingsRepository: AppSettingsRepository
    ): GetAppSettingUseCase = GetAppSettingUseCase(configuration, appSettingsRepository)

    @Singleton
    @Provides
    fun provideGetAppSettingsUseCase(
        configuration: UseCase.Configuration, appSettingsRepository: AppSettingsRepository
    ): GetAppSettingsUseCase = GetAppSettingsUseCase(configuration, appSettingsRepository)

    @Singleton
    @Provides
    fun provideDeleteAppSettingUseCase(
        configuration: UseCase.Configuration, appSettingsRepository: AppSettingsRepository
    ): DeleteAppSettingUseCase = DeleteAppSettingUseCase(configuration, appSettingsRepository)

    @Singleton
    @Provides
    fun provideSaveAppSettingUseCase(
        configuration: UseCase.Configuration, appSettingsRepository: AppSettingsRepository
    ): SaveAppSettingUseCase = SaveAppSettingUseCase(configuration, appSettingsRepository)

    @Singleton
    @Provides
    fun provideCheckpointUseCase(
        configuration: UseCase.Configuration, appSettingsRepository: AppSettingsRepository
    ): CheckpointUseCase = CheckpointUseCase(configuration, appSettingsRepository)
}