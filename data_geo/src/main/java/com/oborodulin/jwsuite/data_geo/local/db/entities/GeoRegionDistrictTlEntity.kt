package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import java.util.Locale
import java.util.UUID

@Entity(
    tableName = GeoRegionDistrictTlEntity.TABLE_NAME,
    indices = [Index(value = ["regionDistrictsId", "regDistrictLocCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoRegionDistrictEntity::class,
        parentColumns = arrayOf("regionDistrictId"),
        childColumns = arrayOf("regionDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class GeoRegionDistrictTlEntity(
    @PrimaryKey val regionDistrictTlId: UUID = UUID.randomUUID(),
    val regDistrictLocCode: String = Locale.getDefault().language,
    val regDistrictName: String,
    @ColumnInfo(index = true) val regionDistrictsId: UUID,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_region_districts_tl"

        fun defaultRegionDistrictTl(
            regionDistrictTlId: UUID = UUID.randomUUID(), regionDistrictId: UUID,
            districtName: String
        ) = GeoRegionDistrictTlEntity(
            regionDistrictTlId = regionDistrictTlId, regionDistrictsId = regionDistrictId,
            regDistrictName = districtName
        )

        fun maryinskyRegionDistrictTl(ctx: Context, regionDistrictId: UUID) =
            defaultRegionDistrictTl(
                districtName = ctx.resources.getString(R.string.def_reg_maryinsky_name),
                regionDistrictId = regionDistrictId
            )

        fun donetskyRegionDistrictTl(ctx: Context, regionDistrictId: UUID) =
            defaultRegionDistrictTl(
                districtName = ctx.resources.getString(R.string.def_reg_donetsky_name),
                regionDistrictId = regionDistrictId
            )

        fun regionDistrictTl(ctx: Context, districtShortName: String, regionDistrictId: UUID) =
            when (districtShortName) {
                ctx.resources.getString(R.string.def_reg_maryinsky_short_name) -> maryinskyRegionDistrictTl(
                    ctx, regionDistrictId
                )

                ctx.resources.getString(R.string.def_reg_donetsky_short_name) -> donetskyRegionDistrictTl(
                    ctx, regionDistrictId
                )

                else -> defaultRegionDistrictTl(
                    regionDistrictId = UUID.randomUUID(), districtName = ""
                )
            }
    }

    override fun id() = this.regionDistrictTlId

    override fun key(): Int {
        var result = regionDistrictsId.hashCode()
        result = result * 31 + regDistrictLocCode.hashCode()
        return result
    }
}


