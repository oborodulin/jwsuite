package com.oborodulin.jwsuite.domain.di

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.appsetting.CheckpointUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.DeleteAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDashboardSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDataManagementSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideGetDashboardSettingsUseCase(
        @ApplicationContext ctx: Context,
        configuration: UseCase.Configuration,
        databaseRepository: DatabaseRepository,
        appSettingsRepository: AppSettingsRepository,
        sessionManagerRepository: SessionManagerRepository,
        membersRepository: MembersRepository
    ): GetDashboardSettingsUseCase = GetDashboardSettingsUseCase(
        ctx,
        configuration,
        databaseRepository,
        appSettingsRepository,
        sessionManagerRepository,
        membersRepository
    )

    @Singleton
    @Provides
    fun provideGetDataManagementSettingsUseCase(
        configuration: UseCase.Configuration,
        appSettingsRepository: AppSettingsRepository,
        sessionManagerRepository: SessionManagerRepository,
        membersRepository: MembersRepository
    ): GetDataManagementSettingsUseCase = GetDataManagementSettingsUseCase(
        configuration,
        appSettingsRepository,
        sessionManagerRepository,
        membersRepository
    )

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
    fun provideSaveAppSettingsUseCase(
        configuration: UseCase.Configuration, appSettingsRepository: AppSettingsRepository
    ): SaveAppSettingsUseCase = SaveAppSettingsUseCase(configuration, appSettingsRepository)

    @Singleton
    @Provides
    fun provideCheckpointUseCase(
        configuration: UseCase.Configuration, appSettingsRepository: AppSettingsRepository
    ): CheckpointUseCase = CheckpointUseCase(configuration, appSettingsRepository)
}