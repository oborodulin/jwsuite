package com.oborodulin.jwsuite.data_appsetting.local.db.repositories

import com.oborodulin.jwsuite.data_appsetting.local.csv.mappers.AppSettingCsvMappers
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_appsetting.local.db.mappers.AppSettingMappers
import com.oborodulin.jwsuite.data_appsetting.local.db.repositories.sources.LocalAppSettingDataSource
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val localAppSettingDataSource: LocalAppSettingDataSource,
    private val domainMappers: AppSettingMappers,
    private val csvMappers: AppSettingCsvMappers
) : AppSettingsRepository {
    override fun getAll() =
        localAppSettingDataSource.getAppSettings()
            .map(domainMappers.appSettingEntityListToAppSettingListMapper::map)

    override fun get(settingId: UUID) =
        localAppSettingDataSource.getAppSetting(settingId)
            .map(domainMappers.appSettingEntityToAppSettingMapper::map)

    override fun save(setting: AppSetting) = flow {
        if (setting.id == null) {
            localAppSettingDataSource.insertAppSetting(
                domainMappers.appSettingToAppSettingEntityMapper.map(setting)
            )
        } else {
            localAppSettingDataSource.updateAppSetting(
                domainMappers.appSettingToAppSettingEntityMapper.map(setting)
            )
        }
        emit(setting)
    }

    override fun save(settings: List<AppSetting>) = flow {
        settings.forEach { localAppSettingDataSource.updateAppSetting(it.paramName, it.paramValue) }
        emit(settings)
    }

    override fun delete(setting: AppSetting) = flow {
        localAppSettingDataSource.deleteAppSetting(
            domainMappers.appSettingToAppSettingEntityMapper.map(
                setting
            )
        )
        this.emit(setting)
    }

    override fun delete(settingId: UUID) = flow {
        localAppSettingDataSource.deleteAppSettingById(settingId)
        this.emit(settingId)
    }

    override fun delete(settings: List<AppSetting>) = flow {
        localAppSettingDataSource.deleteAppSettings(settings.map {
            domainMappers.appSettingToAppSettingEntityMapper.map(it)
        })
        this.emit(settings)
    }

    override suspend fun deleteAll() = localAppSettingDataSource.deleteAppSettings()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = AppSettingEntity.TABLE_NAME)
    override fun extractAppSettings() = localAppSettingDataSource.getAppSettingEntities()
        .map(csvMappers.appSettingEntityListToAppSettingCsvListMapper::map)

    @CsvLoad<AppSettingCsv>(
        fileNamePrefix = AppSettingEntity.TABLE_NAME,
        contentType = AppSettingCsv::class
    )
    override fun loadAppSettings(settings: List<AppSettingCsv>) = flow {
        localAppSettingDataSource.loadAppSettingEntities(
            csvMappers.appSettingCsvListToAppSettingEntityListMapper.map(settings)
        )
        emit(settings.size)
    }
}