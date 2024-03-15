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
    tableName = GeoRegionTlEntity.TABLE_NAME,
    indices = [Index(value = ["regionsId", "regionLocCode", "regionTlCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoRegionEntity::class,
        parentColumns = arrayOf("regionId"),
        childColumns = arrayOf("regionsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoRegionTlEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val regionTlId: UUID = UUID.randomUUID(),
    val regionLocCode: String = Locale.getDefault().language,
    val regionTlCode: String? = null,
    val regionName: String,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val regionsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_regions_tl"

        fun defaultRegionTl(
            regionTlId: UUID = UUID.randomUUID(), regionId: UUID, regionTlCode: String? = null,
            regionName: String
        ) = GeoRegionTlEntity(
            regionTlId = regionTlId, regionTlCode = regionTlCode, regionName = regionName,
            regionsId = regionId
        )

        private fun defRegionTl(ctx: Context, regionId: UUID) = defaultRegionTl(
            regionName = ctx.resources.getString(R.string.def_reg_name), regionId = regionId
        )

        private fun donetskRegionTl(ctx: Context, regionId: UUID) = defaultRegionTl(
            regionName = ctx.resources.getString(R.string.def_reg_donetsk_name), regionId = regionId
        )

        private fun luganskRegionTl(ctx: Context, regionId: UUID) = defaultRegionTl(
            regionName = ctx.resources.getString(R.string.def_reg_luhansk_name), regionId = regionId
        )

        fun regionTl(ctx: Context, regionCode: String, regionId: UUID) =
            when (regionCode) {
                ctx.resources.getString(R.string.def_reg_code) -> defRegionTl(
                    ctx,
                    regionId
                )

                ctx.resources.getString(R.string.def_reg_donetsk_code) -> donetskRegionTl(
                    ctx,
                    regionId
                )

                ctx.resources.getString(R.string.def_reg_luhansk_code) -> luganskRegionTl(
                    ctx,
                    regionId
                )

                else -> defaultRegionTl(regionId = UUID.randomUUID(), regionName = "")
            }
    }

    override fun id() = this.regionTlId

    override fun key(): Int {
        var result = regionsId.hashCode()
        result = result * 31 + regionLocCode.hashCode()
        return result
    }
}


