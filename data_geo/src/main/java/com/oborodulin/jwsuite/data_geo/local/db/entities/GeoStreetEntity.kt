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
import com.oborodulin.jwsuite.domain.types.RoadType
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoStreetEntity.TABLE_NAME,
    indices = [Index(
        value = ["sLocalitiesId", "streetHashCode", "roadType", "isStreetPrivateSector"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("sLocalitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoStreetEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val streetId: UUID = UUID.randomUUID(),
    val streetHashCode: Int,
    val roadType: RoadType = RoadType.STREET,
    val isStreetPrivateSector: Boolean = false,     // all street is private sector
    val estStreetHouses: Int? = null,               // estimated houses of the street
    val streetGeocode: String? = null,
    @ColumnInfo(index = true) val streetOsmId: Long? = null,
    @Embedded(prefix = PREFIX) val coordinates: Coordinates,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val sLocalitiesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_streets"
        const val PREFIX = "street_"

        fun defaultStreet(
            localityId: UUID = UUID.randomUUID(), streetHashCode: Int,
            roadType: RoadType = RoadType.STREET, isPrivateSector: Boolean = false,
            streetGeocode: String? = null,
            streetOsmId: Long? = null, coordinates: Coordinates = Coordinates()
        ) = GeoStreetEntity(
            sLocalitiesId = localityId, streetHashCode = streetHashCode, roadType = roadType,
            isStreetPrivateSector = isPrivateSector,
            streetGeocode = streetGeocode, streetOsmId = streetOsmId, coordinates = coordinates
        )

        fun strelkovojDiviziiStreet(ctx: Context, localityId: UUID = UUID.randomUUID()) =
            defaultStreet(
                localityId = localityId,
                streetHashCode = ctx.resources.getString(R.string.def_strelkovojDivizii_name)
                    .hashCode()
            )

        fun nezavisimostiStreet(ctx: Context, localityId: UUID = UUID.randomUUID()) = defaultStreet(
            localityId = localityId,
            streetHashCode = ctx.resources.getString(R.string.def_nezavisimosti_name).hashCode()
        )

        fun baratynskogoStreet(ctx: Context, localityId: UUID = UUID.randomUUID()) = defaultStreet(
            localityId = localityId,
            streetHashCode = ctx.resources.getString(R.string.def_baratynskogo_name).hashCode(),
            isPrivateSector = true
        )

        fun patorgynskogoStreet(ctx: Context, localityId: UUID = UUID.randomUUID()) = defaultStreet(
            localityId = localityId,
            streetHashCode = ctx.resources.getString(R.string.def_patorgynskogo_name).hashCode(),
            isPrivateSector = true
        )

        fun belogorodskayaStreet(ctx: Context, localityId: UUID = UUID.randomUUID()) =
            defaultStreet(
                localityId = localityId,
                streetHashCode = ctx.resources.getString(R.string.def_belogorodskaya_name)
                    .hashCode(),
                isPrivateSector = true
            )

        fun novomospinoStreet(ctx: Context, localityId: UUID = UUID.randomUUID()) = defaultStreet(
            localityId = localityId,
            streetHashCode = ctx.resources.getString(R.string.def_novomospino_name).hashCode()
        )

        fun gertsenaStreet(ctx: Context, localityId: UUID = UUID.randomUUID()) = defaultStreet(
            localityId = localityId,
            streetHashCode = ctx.resources.getString(R.string.def_gertsena_name).hashCode()
        )
    }

    override fun id() = this.streetId

    override fun key(): Int {
        var result = sLocalitiesId.hashCode()
        result = result * 31 + streetHashCode.hashCode()
        result = result * 31 + roadType.hashCode()
        result = result * 31 + isStreetPrivateSector.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Street Entity '").append(roadType)
            .append("'. OSM: streetOsmId = ").append(streetOsmId)
            .append("; streetGeocode = ").append(streetGeocode)
            .append("; coordinates = ").append(coordinates)
            .append(" [sLocalitiesId = ").append(sLocalitiesId)
            .append("; streetHashCode = ").append(streetHashCode)
            .append("] streetId = ").append(streetId)
        return str.toString()
    }
}