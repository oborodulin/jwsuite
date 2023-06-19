package com.oborodulin.jwsuite.data.local.db.mappers.appsetting

data class AppSettingMappers(
    val appSettingEntityListToAppSettingListMapper: AppSettingEntityListToAppSettingListMapper,
    val appSettingEntityToAppSettingMapper: AppSettingEntityToAppSettingMapper,
    val appSettingToAppSettingEntityMapper: AppSettingToAppSettingEntityMapper
)
