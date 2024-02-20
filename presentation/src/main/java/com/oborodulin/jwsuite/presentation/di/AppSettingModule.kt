package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.appsetting.AppSettingUseCases
import com.oborodulin.jwsuite.domain.usecases.appsetting.DeleteAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDashboardSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDataManagementSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.converters.AppSettingListConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListItemToAppSettingMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListItemToAppSettingsListMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper
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

    @Singleton
    @Provides
    fun provideAppSettingsListItemToAppSettingMapper(): AppSettingsListItemToAppSettingMapper =
        AppSettingsListItemToAppSettingMapper()

    @Singleton
    @Provides
    fun provideAppSettingsListItemToAppSettingsListMapper(mapper: AppSettingsListItemToAppSettingMapper): AppSettingsListItemToAppSettingsListMapper =
        AppSettingsListItemToAppSettingsListMapper(mapper = mapper)

    // CONVERTERS:
    @Singleton
    @Provides
    fun provideAppSettingListConverter(mapper: AppSettingsListToAppSettingsListItemMapper): AppSettingListConverter =
        AppSettingListConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideAppSettingUseCases(
        getAppSettingsUseCase: GetAppSettingsUseCase,
        getDashboardSettingsUseCase: GetDashboardSettingsUseCase,
        getDataManagementSettingsUseCase: GetDataManagementSettingsUseCase,
        getAppSettingUseCase: GetAppSettingUseCase,
        saveAppSettingUseCase: SaveAppSettingUseCase,
        saveAppSettingsUseCase: SaveAppSettingsUseCase,
        deleteAppSettingUseCase: DeleteAppSettingUseCase
    ): AppSettingUseCases = AppSettingUseCases(
        getAppSettingsUseCase,
        getDashboardSettingsUseCase,
        getDataManagementSettingsUseCase,
        getAppSettingUseCase,
        saveAppSettingUseCase,
        saveAppSettingsUseCase,
        deleteAppSettingUseCase
    )
}