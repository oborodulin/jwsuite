package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.util.RoadType
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
data class GeoStreetEntity(
    @PrimaryKey val streetId: UUID = UUID.randomUUID(),
    val streetHashCode: Int,
    val roadType: RoadType = RoadType.STREET,
    val isStreetPrivateSector: Boolean = false,     // all street is private sector
    val estStreetHouses: Int? = null,               // estimated houses of the street
    @ColumnInfo(index = true) val sLocalitiesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_streets"

        fun defaultStreet(
            localityId: UUID = UUID.randomUUID(), streetHashCode: Int,
            roadType: RoadType = RoadType.STREET, isPrivateSector: Boolean = false,
        ) = GeoStreetEntity(
            sLocalitiesId = localityId, streetHashCode = streetHashCode, roadType = roadType,
            isStreetPrivateSector = isPrivateSector
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
        str.append("Street Entity '").append(roadType).append("'")
            .append(" [sLocalitiesId = ").append(sLocalitiesId)
            .append("; streetHashCode = ").append(streetHashCode)
            .append("] streetId = ").append(streetId)
        return str.toString()
    }
}