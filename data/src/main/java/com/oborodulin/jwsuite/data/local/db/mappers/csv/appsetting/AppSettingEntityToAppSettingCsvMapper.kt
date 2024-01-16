package com.oborodulin.jwsuite.data.local.db.mappers.csv.appsetting

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity

class AppSettingEntityToAppSettingCsvMapper : Mapper<AppSettingEntity, com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv> {
    override fun map(input: AppSettingEntity) =
        com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv(
            settingId = input.settingId,
            paramName = input.paramName,
            paramValue = input.paramValue
        )
}