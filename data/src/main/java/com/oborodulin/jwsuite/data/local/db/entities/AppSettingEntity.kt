package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.home.common.util.Utils.Companion.currencyCode
import com.oborodulin.jwsuite.domain.util.AppSettingParam
import java.util.Locale
import java.util.UUID

@Entity(
    tableName = AppSettingEntity.TABLE_NAME,
    indices = [Index(value = ["paramName"], unique = true)]
)
data class AppSettingEntity(
    @PrimaryKey val settingId: UUID = UUID.randomUUID(),
    val paramName: AppSettingParam,
    val paramValue: String
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "app_settings"

        fun defaultParam(
            settingId: UUID = UUID.randomUUID(), paramName: AppSettingParam, paramValue: String
        ) = AppSettingEntity(settingId = settingId, paramName = paramName, paramValue = paramValue)

        fun langParam() = defaultParam(
            paramName = AppSettingParam.LANG, paramValue = Locale.getDefault().language
        )

        fun currencyCodeParam() = defaultParam(
            paramName = AppSettingParam.CURRENCY_CODE, paramValue = currencyCode()
        )

        fun dayMuParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.DAY_MU,
            paramValue = ctx.resources.getString(com.oborodulin.home.common.R.string.day_unit)
        )

        fun monthMuParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.MONTH_MU,
            paramValue = ctx.resources.getString(com.oborodulin.home.common.R.string.month_unit)
        )

        fun personNumMuParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.PERSON_NUM_MU,
            paramValue = ctx.resources.getString(com.oborodulin.home.common.R.string.person_unit)
        )

        fun territoryTotalProcessingPeriodParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_TOTAL_PROCESSING_PERIOD,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_total_processing_period)
                .toString()
        )

        fun territoryProcessingPeriodParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_PROCESSING_PERIOD,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_processing_period)
                .toString()
        )

        fun territoryRoomsLimitParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_ROOMS_LIMIT,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_rooms_limit)
                .toString()
        )

        fun territoryMaxRoomsParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_MAX_ROOMS,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_max_rooms)
                .toString()
        )

        fun territorySettlingPeriodParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_SETTLING_PERIOD,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_settling_period)
                .toString()
        )
    }

    override fun id() = this.settingId

    override fun key() = paramName.hashCode()

    override fun toString(): String {
        val str = StringBuffer()
        str.append("AppSetting Entity: ").append(paramName).append(" = ").append("'")
            .append(paramValue)
            .append("' settingId = ").append(settingId)
        return str.toString()
    }
}