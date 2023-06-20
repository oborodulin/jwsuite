package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.util.RoadType
import java.util.UUID

@Entity(
    tableName = GeoStreetEntity.TABLE_NAME,
    indices = [Index(
        value = ["localitiesId", "streetHashCode", "roadType", "isPrivateSector"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("localitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class GeoStreetEntity(
    @PrimaryKey val streetId: UUID = UUID.randomUUID(),
    val streetHashCode: Int,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,   // all street is private sector
    val estimatedHouses: Int? = null,       // estimated houses of the street
    @ColumnInfo(index = true) val localitiesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_streets"

        fun defaultStreet(
            localityId: UUID = UUID.randomUUID(), streetHashCode: Int,
            roadType: RoadType = RoadType.STREET, isPrivateSector: Boolean = false,
        ) = GeoStreetEntity(
            localitiesId = localityId, streetHashCode = streetHashCode, roadType = roadType,
            isPrivateSector = isPrivateSector
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
        var result = localitiesId.hashCode()
        result = result * 31 + streetHashCode.hashCode()
        result = result * 31 + roadType.hashCode()
        result = result * 31 + isPrivateSector.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Street Entity '").append(roadType).append("'")
            .append(" [localitiesId = ").append(localitiesId)
            .append("; streetHashCode = ").append(streetHashCode)
            .append("] streetId = ").append(streetId)
        return str.toString()
    }
}