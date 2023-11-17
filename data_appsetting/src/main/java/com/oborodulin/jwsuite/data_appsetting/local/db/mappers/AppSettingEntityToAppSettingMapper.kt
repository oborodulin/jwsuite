package com.oborodulin.jwsuite.data_appsetting.local.db.mappers

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting

class AppSettingEntityToAppSettingMapper(private val ctx: Context) :
    Mapper<AppSettingEntity, AppSetting> {
    override fun map(input: AppSettingEntity): AppSetting {
        val appSetting = AppSetting(
            ctx = ctx,
            paramName = input.paramName,
            paramValue = input.paramValue,
        )
        appSetting.id = input.settingId
        return appSetting
    }
}