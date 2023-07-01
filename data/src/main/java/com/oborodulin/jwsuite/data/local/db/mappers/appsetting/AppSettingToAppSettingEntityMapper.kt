package com.oborodulin.jwsuite.data.local.db.mappers.appsetting

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.model.AppSetting
import java.util.UUID

class AppSettingToAppSettingEntityMapper : Mapper<AppSetting, AppSettingEntity> {
    override fun map(input: AppSetting) = AppSettingEntity(
        settingId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        paramName = input.paramName,
        paramValue = input.paramValue
    )
}