package com.oborodulin.jwsuite.data_appsetting.local.csv.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv

class AppSettingCsvToAppSettingEntityMapper : Mapper<AppSettingCsv, AppSettingEntity> {
    override fun map(input: AppSettingCsv) = AppSettingEntity(
        settingId = input.settingId,
        paramName = input.paramName,
        paramValue = input.paramValue
    )
}