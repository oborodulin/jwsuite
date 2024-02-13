package com.oborodulin.jwsuite.domain.usecases.appsetting

data class AppSettingUseCases(
    val getAppSettingsUseCase: GetAppSettingsUseCase,
    val getDashboardSettingsUseCase: GetDashboardSettingsUseCase,
    val getAppSettingUseCase: GetAppSettingUseCase,
    val saveAppSettingUseCase: SaveAppSettingUseCase,
    val saveAppSettingsUseCase: SaveAppSettingsUseCase,
    val deleteAppSettingUseCase: DeleteAppSettingUseCase
)