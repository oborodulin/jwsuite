package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import kotlinx.serialization.Serializable
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
@Serializable
data class GeoRegionDistrictEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val regionDistrictId: UUID = UUID.randomUUID(),
    val regDistrictShortName: String,
    val regDistrictGeocode: String? = null,
    @ColumnInfo(index = true) val regDistrictOsmId: Long? = null,
    @Embedded(prefix = PREFIX) val coordinates: Coordinates,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val rRegionsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_region_districts"
        const val PREFIX = "regDistrict_"
        const val PX = "rd_"
        const val PX_LOCALITY = "lrd_"
        const val PX_LOCALITY_DISTRICT = "ldrd_"
        const val PX_MICRODISTRICT = "mrd_"

        fun defaultRegionDistrict(
            regionDistrictId: UUID = UUID.randomUUID(), regionId: UUID = UUID.randomUUID(),
            districtShortName: String,
            regDistrictGeocode: String? = null,
            regDistrictOsmId: Long? = null, coordinates: Coordinates = Coordinates()
        ) = GeoRegionDistrictEntity(
            rRegionsId = regionId, regionDistrictId = regionDistrictId,
            regDistrictShortName = districtShortName,
            regDistrictGeocode = regDistrictGeocode, regDistrictOsmId = regDistrictOsmId,
            coordinates = coordinates
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
        str.append("Region District Entity '").append(regDistrictShortName)
            .append("'. OSM: regDistrictOsmId = ").append(regDistrictOsmId)
            .append("; regDistrictGeocode = ").append(regDistrictGeocode)
            .append("; coordinates = ").append(coordinates)
            .append(" [rRegionsId = ").append(rRegionsId)
            .append("] regionDistrictId = ").append(regionDistrictId)
        return str.toString()
    }
}