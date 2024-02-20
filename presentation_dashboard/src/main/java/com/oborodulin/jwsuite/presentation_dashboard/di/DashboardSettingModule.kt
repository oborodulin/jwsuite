package com.oborodulin.jwsuite.presentation_dashboard.di

import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.role.MemberRolesListToMemberRolesListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role.transfer.RoleTransferObjectsListToRoleTransferObjectsListItemMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DashboardSettingUiModelConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DataManagementSettingUiModelConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardSettingModule {
    // MAPPERS:

    // CONVERTERS:
    @Singleton
    @Provides
    fun provideDashboardSettingUiModelConverter(
        settingsMapper: AppSettingsListToAppSettingsListItemMapper,
        rolesMapper: MemberRolesListToMemberRolesListItemMapper
    ): DashboardSettingUiModelConverter = DashboardSettingUiModelConverter(
        settingsMapper = settingsMapper,
        rolesMapper = rolesMapper
    )

    @Singleton
    @Provides
    fun provideDataManagementSettingUiModelConverter(
        settingsMapper: AppSettingsListToAppSettingsListItemMapper,
        transferObjectsMapper: RoleTransferObjectsListToRoleTransferObjectsListItemMapper
    ): DataManagementSettingUiModelConverter = DataManagementSettingUiModelConverter(
        settingsMapper = settingsMapper,
        transferObjectsMapper = transferObjectsMapper
    )

    // USE CASES:
}