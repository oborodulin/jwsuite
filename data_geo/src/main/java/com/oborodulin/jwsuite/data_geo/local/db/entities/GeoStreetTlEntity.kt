package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import kotlinx.serialization.Serializable
import java.util.Locale
import java.util.UUID

@Entity(
    tableName = GeoStreetTlEntity.TABLE_NAME,
    indices = [Index(value = ["streetsId", "streetLocCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoStreetEntity::class,
        parentColumns = arrayOf("streetId"),
        childColumns = arrayOf("streetsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoStreetTlEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val streetTlId: UUID = UUID.randomUUID(),
    val streetLocCode: String = Locale.getDefault().language,
    val streetName: String,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val streetsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_streets_tl"

        fun defaultStreetTl(
            streetTlId: UUID = UUID.randomUUID(), streetId: UUID, streetName: String
        ) = GeoStreetTlEntity(
            streetTlId = streetTlId, streetName = streetName, streetsId = streetId
        )

        fun strelkovojDiviziiStreetTl(ctx: Context, streetId: UUID) = defaultStreetTl(
            streetId = streetId,
            streetName = ctx.resources.getString(R.string.def_strelkovojDivizii_name)
        )

        fun nezavisimostiStreetTl(ctx: Context, streetId: UUID) = defaultStreetTl(
            streetId = streetId,
            streetName = ctx.resources.getString(R.string.def_nezavisimosti_name)
        )

        fun baratynskogoStreetTl(ctx: Context, streetId: UUID) = defaultStreetTl(
            streetId = streetId,
            streetName = ctx.resources.getString(R.string.def_baratynskogo_name)
        )

        fun patorgynskogoStreetTl(ctx: Context, streetId: UUID) = defaultStreetTl(
            streetId = streetId,
            streetName = ctx.resources.getString(R.string.def_patorgynskogo_name)
        )

        fun belogorodskayaStreetTl(ctx: Context, streetId: UUID) = defaultStreetTl(
            streetId = streetId,
            streetName = ctx.resources.getString(R.string.def_belogorodskaya_name)
        )

        fun novomospinoStreetTl(ctx: Context, streetId: UUID) = defaultStreetTl(
            streetId = streetId,
            streetName = ctx.resources.getString(R.string.def_novomospino_name)
        )

        fun gertsenaStreetTl(ctx: Context, streetId: UUID) = defaultStreetTl(
            streetId = streetId,
            streetName = ctx.resources.getString(R.string.def_gertsena_name)
        )

        fun streetTl(ctx: Context, streetHashCode: Int?, streetId: UUID) =
            when (streetHashCode) {
                ctx.resources.getString(R.string.def_strelkovojDivizii_name)
                    .hashCode() -> strelkovojDiviziiStreetTl(ctx, streetId)

                ctx.resources.getString(R.string.def_nezavisimosti_name)
                    .hashCode() -> nezavisimostiStreetTl(ctx, streetId)

                ctx.resources.getString(R.string.def_baratynskogo_name)
                    .hashCode() -> baratynskogoStreetTl(ctx, streetId)

                ctx.resources.getString(R.string.def_patorgynskogo_name)
                    .hashCode() -> patorgynskogoStreetTl(ctx, streetId)

                ctx.resources.getString(R.string.def_belogorodskaya_name)
                    .hashCode() -> belogorodskayaStreetTl(ctx, streetId)

                ctx.resources.getString(R.string.def_novomospino_name)
                    .hashCode() -> novomospinoStreetTl(ctx, streetId)

                ctx.resources.getString(R.string.def_gertsena_name)
                    .hashCode() -> gertsenaStreetTl(ctx, streetId)

                else -> defaultStreetTl(streetId = UUID.randomUUID(), streetName = "")
            }
    }

    override fun id() = this.streetTlId

    override fun key(): Int {
        var result = streetsId.hashCode()
        result = result * 31 + streetLocCode.hashCode()
        return result
    }
}


