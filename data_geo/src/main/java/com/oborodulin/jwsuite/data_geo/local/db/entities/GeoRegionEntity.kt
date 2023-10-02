package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoRegionEntity.TABLE_NAME,
    indices = [Index(value = ["regionCode"], unique = true)],
)
@Serializable
data class GeoRegionEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val regionId: UUID = UUID.randomUUID(),
    val regionCode: String
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_regions"

        fun defaultRegion(
            regionId: UUID = UUID.randomUUID(), regionCode: String
        ) = GeoRegionEntity(regionId = regionId, regionCode = regionCode)

        fun donetskRegion(ctx: Context) = defaultRegion(
            regionCode = ctx.resources.getString(R.string.def_reg_donetsk_code)
        )

        fun luganskRegion(ctx: Context) = defaultRegion(
            regionCode = ctx.resources.getString(R.string.def_reg_luhansk_code)
        )
    }

    override fun id() = this.regionId

    override fun key() = this.regionCode.hashCode()

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Region Entity №").append(regionCode).append(" regionId = ").append(regionId)
        return str.toString()
    }
}