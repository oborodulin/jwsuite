package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.AppSetting
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AppSettingsRepository {
    fun getAll(): Flow<List<AppSetting>>
    fun get(settingId: UUID): Flow<AppSetting>
    fun save(setting: AppSetting): Flow<AppSetting>
    fun delete(setting: AppSetting): Flow<AppSetting>
    fun deleteById(settingId: UUID): Flow<UUID>
    fun delete(settings: List<AppSetting>): Flow<List<AppSetting>>
    suspend fun deleteAll()

    // API:
    suspend fun checkpoint(): Int
}