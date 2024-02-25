package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoCountryEntity.TABLE_NAME,
    indices = [Index(value = ["countryCode"], unique = true)],
)
@Serializable
data class GeoCountryEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val countryId: UUID = UUID.randomUUID(),
    val countryCode: String,
    val countryGeocode: String? = null,
    @ColumnInfo(index = true) val countryOsmId: Long? = null,
    @Embedded(prefix = PREFIX) val coordinates: Coordinates
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_countries"
        const val PREFIX = "country_"
        const val PX = "n_"
        const val PX_REGION = "nr_"
        const val PX_DISTRICT = "ndr_"
        const val PX_LOCALITY = "nl_"
        const val PX_LOCALITY_DISTRICT = "nld_"
        const val PX_MICRODISTRICT = "nm_"

        fun defaultCountry(
            countryId: UUID = UUID.randomUUID(),
            countryCode: String,
            countryOsmId: Long? = null,
            coordinates: Coordinates = Coordinates(),
            countryGeocode: String? = null
        ) = GeoCountryEntity(
            countryId = countryId, countryCode = countryCode,
            countryGeocode = countryGeocode, countryOsmId = countryOsmId, coordinates = coordinates
        )

        fun defCountry(ctx: Context) = defaultCountry(
            countryCode = ctx.resources.getString(R.string.def_reg_code)
        )

        fun ukraineCountry(ctx: Context) = defaultCountry(
            countryCode = ctx.resources.getString(R.string.def_reg_donetsk_code)
        )

        fun russiaCountry(ctx: Context) = defaultCountry(
            countryCode = ctx.resources.getString(R.string.def_reg_luhansk_code)
        )
    }

    override fun id() = this.countryId
    override fun key() = countryCode.hashCode()
    override fun toString(): String {
        val str = StringBuffer()
        str.append("Country Entity countryCode = '").append(countryCode)
            .append("'. OSM: countryOsmId = ").append(countryOsmId)
            .append("; countryGeocode = ").append(countryGeocode)
            .append("; coordinates = ").append(coordinates)
            .append(" countryId = ").append(countryId)
        return str.toString()
    }
}