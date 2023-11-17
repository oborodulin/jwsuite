package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.appsetting.AppSettingUseCases
import com.oborodulin.jwsuite.domain.usecases.appsetting.DeleteAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingUseCase
import com.oborodulin.jwsuite.presentation.ui.model.converters.AppSettingUiModelConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.AppSettingToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.RolesListToRolesListItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppSettingModule {
    // MAPPERS:
    @Singleton
    @Provides
    fun provideAppSettingToAppSettingsListItemMapper(): AppSettingToAppSettingsListItemMapper =
        AppSettingToAppSettingsListItemMapper()

    @Singleton
    @Provides
    fun provideAppSettingsListToAppSettingsListItemMapper(mapper: AppSettingToAppSettingsListItemMapper): AppSettingsListToAppSettingsListItemMapper =
        AppSettingsListToAppSettingsListItemMapper(mapper = mapper)

    // CONVERTERS:
    @Singleton
    @Provides
    fun provideAppSettingUiModelConverter(
        settingsMapper: AppSettingsListToAppSettingsListItemMapper,
        rolesMapper: RolesListToRolesListItemMapper
    ): AppSettingUiModelConverter =
        AppSettingUiModelConverter(settingsMapper = settingsMapper, rolesMapper = rolesMapper)


    // USE CASES:
    @Singleton
    @Provides
    fun provideAppSettingUseCases(
        getAppSettingsUseCase: GetAppSettingsUseCase,
        getAppSettingUseCase: GetAppSettingUseCase,
        saveAppSettingUseCase: SaveAppSettingUseCase,
        deleteAppSettingUseCase: DeleteAppSettingUseCase
    ): AppSettingUseCases = AppSettingUseCases(
        getAppSettingsUseCase,
        getAppSettingUseCase,
        saveAppSettingUseCase,
        deleteAppSettingUseCase
    )
}