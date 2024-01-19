package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AppSettingsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<AppSetting>>
    fun get(settingId: UUID): Flow<AppSetting>
    fun save(setting: AppSetting): Flow<AppSetting>
    fun save(settings: List<AppSetting>): Flow<List<AppSetting>>
    fun delete(setting: AppSetting): Flow<AppSetting>
    fun deleteById(settingId: UUID): Flow<UUID>
    fun delete(settings: List<AppSetting>): Flow<List<AppSetting>>
    suspend fun deleteAll()
    fun extractAppSettings(): Flow<List<AppSettingCsv>>
    fun loadAppSettings(settings: List<AppSettingCsv>): Flow<Int>
}