package com.oborodulin.jwsuite.data_appsetting.local.db.repositories.sources

import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalAppSettingDataSource {
    fun getAppSettings(): Flow<List<AppSettingEntity>>
    fun getAppSetting(settingId: UUID): Flow<AppSettingEntity>
    suspend fun insertAppSetting(setting: AppSettingEntity)
    suspend fun updateAppSetting(setting: AppSettingEntity)
    suspend fun updateAppSetting(paramName: AppSettingParam, paramValue: String)
    suspend fun deleteAppSetting(setting: AppSettingEntity)
    suspend fun deleteAppSettingById(settingId: UUID)
    suspend fun deleteAppSettings(settings: List<AppSettingEntity>)
    suspend fun deleteAppSettings()
}