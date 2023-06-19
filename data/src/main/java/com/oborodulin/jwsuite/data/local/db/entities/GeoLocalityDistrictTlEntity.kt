package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import java.util.Locale
import java.util.UUID

@Entity(
    tableName = GeoLocalityDistrictTlEntity.TABLE_NAME,
    indices = [Index(value = ["localityDistrictsId", "districtLocCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("localityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class GeoLocalityDistrictTlEntity(
    @PrimaryKey val localityDistrictTlId: UUID = UUID.randomUUID(),
    val districtLocCode: String = Locale.getDefault().language,
    val districtName: String,
    @ColumnInfo(index = true) val localityDistrictsId: UUID,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_locality_districts_tl"

        fun defaultLocalityDistrictTl(
            localityDistrictTlId: UUID = UUID.randomUUID(), localityDistrictId: UUID,
            districtName: String
        ) = GeoLocalityDistrictTlEntity(
            localityDistrictTlId = localityDistrictTlId,
            districtName = districtName,
            localityDistrictsId = localityDistrictId
        )

        fun bdnLocalityDistrictTl(ctx: Context, localityDistrictId: UUID) =
            defaultLocalityDistrictTl(
                districtName = ctx.resources.getString(R.string.def_budyonovsky_name),
                localityDistrictId = localityDistrictId
            )

        fun kvkLocalityDistrictTl(ctx: Context, localityDistrictId: UUID) =
            defaultLocalityDistrictTl(
                districtName = ctx.resources.getString(R.string.def_kievsky_name),
                localityDistrictId = localityDistrictId
            )

        fun kbshLocalityDistrictTl(ctx: Context, localityDistrictId: UUID) =
            defaultLocalityDistrictTl(
                districtName = ctx.resources.getString(R.string.def_kuybyshevsky_name),
                localityDistrictId = localityDistrictId
            )

        fun vrshLocalityDistrictTl(ctx: Context, localityDistrictId: UUID) =
            defaultLocalityDistrictTl(
                districtName = ctx.resources.getString(R.string.def_voroshilovsky_name),
                localityDistrictId = localityDistrictId
            )

        fun localityDistrictTl(ctx: Context, districtShortName: String, localityDistrictId: UUID) =
            when (districtShortName) {
                ctx.resources.getString(R.string.def_budyonovsky_short_name) -> bdnLocalityDistrictTl(
                    ctx, localityDistrictId
                )

                ctx.resources.getString(R.string.def_kievsky_short_name) -> kvkLocalityDistrictTl(
                    ctx, localityDistrictId
                )

                ctx.resources.getString(R.string.def_kuybyshevsky_short_name) -> kbshLocalityDistrictTl(
                    ctx, localityDistrictId
                )

                ctx.resources.getString(R.string.def_voroshilovsky_short_name) -> vrshLocalityDistrictTl(
                    ctx, localityDistrictId
                )

                else -> defaultLocalityDistrictTl(
                    localityDistrictId = UUID.randomUUID(),
                    districtName = ""
                )
            }
    }

    override fun id() = this.localityDistrictTlId

    override fun key(): Int {
        var result = localityDistrictsId.hashCode()
        result = result * 31 + districtLocCode.hashCode()
        return result
    }
}


