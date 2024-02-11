package com.oborodulin.jwsuite.data_appsetting.local.db.entities

import android.content.Context
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.R
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.home.common.util.Utils.Companion.currencyCode
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import kotlinx.serialization.Serializable
import java.util.Locale
import java.util.UUID

@Entity(
    tableName = AppSettingEntity.TABLE_NAME,
    indices = [Index(value = ["paramName"], unique = true)]
)
@Serializable
data class AppSettingEntity(
    @Serializable(with = UUIDSerializer::class)
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

        fun allItemsParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.ALL_ITEMS,
            paramValue = ctx.resources.getString(R.string.all_items_val)
        )

        fun dayMuParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.DAY_MU,
            paramValue = ctx.resources.getString(R.string.day_unit)
        )

        fun monthMuParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.MONTH_MU,
            paramValue = ctx.resources.getString(R.string.month_unit)
        )

        fun yearMuParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.YEAR_MU,
            paramValue = ctx.resources.getString(R.string.year_unit)
        )

        fun personNumMuParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.PERSON_NUM_MU,
            paramValue = ctx.resources.getString(R.string.person_unit)
        )

        fun databaseBackupPeriodParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.DATABASE_BACKUP_PERIOD,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.database_backup_period)
                .toString()
        )

        fun territoryBusinessMarkParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_BUSINESS_MARK,
            paramValue = ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.territory_business_mark)
        )

        fun territoryProcessingPeriodParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_PROCESSING_PERIOD,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_processing_period)
                .toString()
        )

        fun territoryAtHandPeriodParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_AT_HAND_PERIOD,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_at_hand_period)
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

        fun territoryIdlePeriodParam(ctx: Context) = defaultParam(
            paramName = AppSettingParam.TERRITORY_IDLE_PERIOD,
            paramValue = ctx.resources.getInteger(com.oborodulin.jwsuite.domain.R.integer.territory_idle_period)
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