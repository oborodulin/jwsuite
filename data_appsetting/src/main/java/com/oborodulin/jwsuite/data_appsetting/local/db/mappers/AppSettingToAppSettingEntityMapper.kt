package com.oborodulin.jwsuite.data_appsetting.local.db.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import java.util.UUID

class AppSettingToAppSettingEntityMapper : Mapper<AppSetting, AppSettingEntity> {
    override fun map(input: AppSetting) =
        com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity(
            settingId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
            paramName = input.paramName,
            paramValue = input.paramValue
        )
}