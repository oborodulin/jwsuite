package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.OsmData
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
    @Embedded(prefix = PREFIX) val osm: OsmData
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
            osm: OsmData = OsmData()
        ) = GeoCountryEntity(countryId = countryId, countryCode = countryCode, osm = osm)

        fun defCountry(ctx: Context) = defaultCountry(
            countryCode = ctx.resources.getString(R.string.def_country_code)
        )

        fun ukraineCountry(ctx: Context) = defaultCountry(
            countryCode = ctx.resources.getString(R.string.def_country_ukraine_code)
        )

        fun russiaCountry(ctx: Context) = defaultCountry(
            countryCode = ctx.resources.getString(R.string.def_country_russia_code)
        )
    }

    override fun id() = this.countryId
    override fun key() = countryCode.hashCode()
    override fun toString(): String {
        val str = StringBuffer()
        str.append("CountryEntity(countryCode = '").append(countryCode)
            .append("'; ").append(osm)
            .append("; countryId = ").append(countryId).append(")")
        return str.toString()
    }
}