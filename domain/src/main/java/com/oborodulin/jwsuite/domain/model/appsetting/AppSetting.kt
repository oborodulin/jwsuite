package com.oborodulin.jwsuite.domain.model.appsetting

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.util.AppSettingParam

data class AppSetting(
    val ctx: Context? = null,
    val paramName: AppSettingParam,
    val paramValue: String = ""
) : DomainModel() {
    val paramFullName = when (paramName) {
        AppSettingParam.TERRITORY_PROCESSING_PERIOD -> ctx?.resources?.getString(R.string.param_name_territory_processing_period)
            .orEmpty()

        AppSettingParam.TERRITORY_AT_HAND_PERIOD -> ctx?.resources?.getString(R.string.param_name_territory_at_hand_period)
            .orEmpty()

        AppSettingParam.TERRITORY_IDLE_PERIOD -> ctx?.resources?.getString(R.string.param_name_territory_idle_period)
            .orEmpty()

        AppSettingParam.TERRITORY_ROOMS_LIMIT -> ctx?.resources?.getString(R.string.param_name_territory_rooms_limit)
            .orEmpty()

        AppSettingParam.TERRITORY_MAX_ROOMS -> ctx?.resources?.getString(R.string.param_name_territory_max_rooms)
            .orEmpty()

        else -> ""
    }
}
