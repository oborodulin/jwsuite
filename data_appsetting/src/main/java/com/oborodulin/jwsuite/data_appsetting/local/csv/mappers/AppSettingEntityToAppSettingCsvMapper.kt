package com.oborodulin.jwsuite.data_appsetting.local.csv.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv

class AppSettingEntityToAppSettingCsvMapper : Mapper<AppSettingEntity, AppSettingCsv> {
    override fun map(input: AppSettingEntity) = AppSettingCsv(
        settingId = input.settingId,
        paramName = input.paramName,
        paramValue = input.paramValue
    )
}