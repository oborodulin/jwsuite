package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.appsetting.AppSettingUseCases
import com.oborodulin.jwsuite.domain.usecases.appsetting.DeleteAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.converters.AppSettingUiModelConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.MemberRoleToMemberRolesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.MemberRolesListToMemberRolesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.RoleToRolesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListItemToAppSettingMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListItemToAppSettingsListMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.transfer.RoleTransferObjectToRoleTransferObjectsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.transfer.RoleTransferObjectsListToRoleTransferObjectsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.transfer.TransferObjectToTransferObjectsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.transfer.TransferObjectsListToTransferObjectsListItemMapper
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

    @Singleton
    @Provides
    fun provideMemberRoleToMemberRolesListItemMapper(mapper: RoleToRolesListItemMapper): MemberRoleToMemberRolesListItemMapper =
        MemberRoleToMemberRolesListItemMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberRolesListToMemberRolesListItemMapper(mapper: MemberRoleToMemberRolesListItemMapper): MemberRolesListToMemberRolesListItemMapper =
        MemberRolesListToMemberRolesListItemMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTransferObjectToTransferObjectsListItemMapper(): TransferObjectToTransferObjectsListItemMapper =
        TransferObjectToTransferObjectsListItemMapper()

    @Singleton
    @Provides
    fun provideTransferObjectsListToTransferObjectsListItemMapper(mapper: TransferObjectToTransferObjectsListItemMapper): TransferObjectsListToTransferObjectsListItemMapper =
        TransferObjectsListToTransferObjectsListItemMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoleTransferObjectToRoleTransferObjectsListItemMapper(mapper: TransferObjectToTransferObjectsListItemMapper): RoleTransferObjectToRoleTransferObjectsListItemMapper =
        RoleTransferObjectToRoleTransferObjectsListItemMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoleTransferObjectsListToRoleTransferObjectsListItemMapper(mapper: RoleTransferObjectToRoleTransferObjectsListItemMapper): RoleTransferObjectsListToRoleTransferObjectsListItemMapper =
        RoleTransferObjectsListToRoleTransferObjectsListItemMapper(mapper = mapper)

    // CONVERTERS:
    @Singleton
    @Provides
    fun provideAppSettingUiModelConverter(
        settingsMapper: AppSettingsListToAppSettingsListItemMapper,
        rolesMapper: MemberRolesListToMemberRolesListItemMapper,
        transferObjectsMapper: RoleTransferObjectsListToRoleTransferObjectsListItemMapper
    ): AppSettingUiModelConverter = AppSettingUiModelConverter(
        settingsMapper = settingsMapper,
        rolesMapper = rolesMapper,
        transferObjectsMapper = transferObjectsMapper
    )


    // USE CASES:
    @Singleton
    @Provides
    fun provideAppSettingUseCases(
        getAppSettingsUseCase: GetAppSettingsUseCase,
        getAppSettingUseCase: GetAppSettingUseCase,
        saveAppSettingUseCase: SaveAppSettingUseCase,
        saveAppSettingsUseCase: SaveAppSettingsUseCase,
        deleteAppSettingUseCase: DeleteAppSettingUseCase
    ): AppSettingUseCases = AppSettingUseCases(
        getAppSettingsUseCase,
        getAppSettingUseCase,
        saveAppSettingUseCase,
        saveAppSettingsUseCase,
        deleteAppSettingUseCase
    )
}