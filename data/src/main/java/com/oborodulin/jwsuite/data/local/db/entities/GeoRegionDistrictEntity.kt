package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import java.util.UUID

@Entity(
    tableName = GeoRegionDistrictEntity.TABLE_NAME,
    indices = [Index(value = ["rRegionsId", "regDistrictShortName"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoRegionEntity::class,
        parentColumns = arrayOf("regionId"),
        childColumns = arrayOf("rRegionsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class GeoRegionDistrictEntity(
    @PrimaryKey val regionDistrictId: UUID = UUID.randomUUID(),
    val regDistrictShortName: String,
    @ColumnInfo(index = true) val rRegionsId: UUID,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_region_districts"

        fun defaultRegionDistrict(
            regionDistrictId: UUID = UUID.randomUUID(), regionId: UUID = UUID.randomUUID(),
            districtShortName: String
        ) = GeoRegionDistrictEntity(
            rRegionsId = regionId, regionDistrictId = regionDistrictId,
            regDistrictShortName = districtShortName
        )

        fun maryinskyRegionDistrict(ctx: Context, regionId: UUID) = defaultRegionDistrict(
            districtShortName = ctx.resources.getString(R.string.def_reg_maryinsky_short_name),
            regionId = regionId
        )

        fun donetskyRegionDistrict(ctx: Context, regionId: UUID) = defaultRegionDistrict(
            districtShortName = ctx.resources.getString(R.string.def_reg_donetsky_short_name),
            regionId = regionId
        )

    }

    override fun id() = this.regionDistrictId

    override fun key(): Int {
        var result = rRegionsId.hashCode()
        result = result * 31 + regDistrictShortName.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Region District Entity '").append(regDistrictShortName).append("' ")
            .append(" [regionsId = ").append(rRegionsId)
            .append("] regionDistrictId = ").append(regionDistrictId)
        return str.toString()
    }
}