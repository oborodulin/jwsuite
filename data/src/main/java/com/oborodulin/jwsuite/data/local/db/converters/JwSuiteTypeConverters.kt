package com.oborodulin.jwsuite.data.local.db.converters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.oborodulin.home.common.util.Constants
import com.oborodulin.home.common.util.Constants.CONV_COEFF_BIGDECIMAL
import com.oborodulin.jwsuite.domain.util.AppSettingParam
import com.oborodulin.jwsuite.domain.util.LocalityType
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.domain.util.RoadType
import com.oborodulin.jwsuite.domain.util.VillageType
import java.math.BigDecimal
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object JwSuiteTypeConverters {
    private val formatter =
        DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)//.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toUUID(uuid: String?): UUID? = uuid?.let { UUID.fromString(it) }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? = millisSinceEpoch?.let { Date(it) }

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? = value?.let {
        formatter.parse(value, OffsetDateTime::from)
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? = date?.format(formatter)

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? =
        date?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(millisSinceEpoch: Long?): LocalDate? = millisSinceEpoch?.let {
        Instant.ofEpochMilli(millisSinceEpoch).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDateTime(time: LocalDateTime?): Long? =
        time?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDateTime(millisSinceEpoch: Long?): LocalDateTime? =
        millisSinceEpoch?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(millisSinceEpoch),
                ZoneId.systemDefault()
            )
        }

    @TypeConverter
    fun toBigDecimal(value: Long?): BigDecimal? =
        value?.let { BigDecimal(it).divide(BigDecimal(CONV_COEFF_BIGDECIMAL)) }

    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal?): Long? =
        bigDecimal?.multiply(BigDecimal(CONV_COEFF_BIGDECIMAL))?.toLong()

    @TypeConverter
    fun toLocalityType(value: String) = enumValueOf<LocalityType>(value)

    @TypeConverter
    fun fromLocalityType(value: LocalityType) = value.name

    @TypeConverter
    fun toVillageType(value: String) = enumValueOf<VillageType>(value)

    @TypeConverter
    fun fromVillageType(value: VillageType) = value.name

    @TypeConverter
    fun toRoadType(value: String) = enumValueOf<RoadType>(value)

    @TypeConverter
    fun fromRoadType(value: RoadType) = value.name

    @TypeConverter
    fun toMemberType(value: String) = enumValueOf<MemberType>(value)

    @TypeConverter
    fun fromMemberType(value: MemberType) = value.name

    @TypeConverter
    fun toAppSettingParam(value: String) = enumValueOf<AppSettingParam>(value)

    @TypeConverter
    fun fromAppSettingParam(value: AppSettingParam) = value.name
}