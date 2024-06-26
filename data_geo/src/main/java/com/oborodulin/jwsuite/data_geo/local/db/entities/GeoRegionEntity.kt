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
import com.oborodulin.jwsuite.domain.types.RegionType
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoRegionEntity.TABLE_NAME,
    indices = [Index(value = ["rCountriesId", "regionType", "regionCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoCountryEntity::class,
        parentColumns = arrayOf("countryId"),
        childColumns = arrayOf("rCountriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoRegionEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val regionId: UUID = UUID.randomUUID(),
    val regionCode: String,
    val regionType: RegionType = RegionType.REGION,
    val isRegionTypePrefix: Boolean = false,
    val regionGeocode: String? = null,
    @ColumnInfo(index = true) val regionOsmId: Long? = null,
    @Embedded(prefix = PREFIX) val coordinates: Coordinates,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val rCountriesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_regions"
        const val PREFIX = "region_"
        const val PX = "r_"
        const val PX_DISTRICT = "dr_"
        const val PX_LOCALITY = "lr_"
        const val PX_LOCALITY_DISTRICT = "ldr_"
        const val PX_MICRODISTRICT = "mr_"

        fun defaultRegion(
            countryId: UUID = UUID.randomUUID(),
            regionId: UUID = UUID.randomUUID(),
            regionCode: String, regionType: RegionType = RegionType.REGION,
            isRegionTypePrefix: Boolean = false,
            regionGeocode: String? = null, regionOsmId: Long? = null,
            coordinates: Coordinates = Coordinates()
        ) = GeoRegionEntity(
            rCountriesId = countryId, regionId = regionId, regionCode = regionCode,
            regionType = regionType, isRegionTypePrefix = isRegionTypePrefix,
            regionGeocode = regionGeocode, regionOsmId = regionOsmId, coordinates = coordinates
        )

        fun defRegion(ctx: Context, countryId: UUID) = defaultRegion(
            regionCode = ctx.resources.getString(R.string.def_reg_code),
            countryId = countryId
        )

        fun donetskRegion(ctx: Context, countryId: UUID) = defaultRegion(
            regionCode = ctx.resources.getString(R.string.def_reg_donetsk_code),
            countryId = countryId
        )

        fun luganskRegion(ctx: Context, countryId: UUID) = defaultRegion(
            regionCode = ctx.resources.getString(R.string.def_reg_luhansk_code),
            countryId = countryId
        )
    }

    override fun id() = this.regionId

    override fun key(): Int {
        var result = rCountriesId.hashCode()
        result = result * 31 + regionType.hashCode()
        result = result * 31 + regionCode.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("RegionEntity(regionCode = ").append(regionCode)
            .append("; regionType = ").append(regionType)
            .append("; isRegionTypePrefix = ").append(isRegionTypePrefix)
            .append("; OSM(regionOsmId = ").append(regionOsmId)
            .append("; regionGeocode = ").append(regionGeocode)
            .append("; coordinates = ").append(coordinates)
            .append("); [rCountriesId = ").append(rCountriesId)
            .append("]; regionId = ").append(regionId).append(")")
        return str.toString()
    }
}