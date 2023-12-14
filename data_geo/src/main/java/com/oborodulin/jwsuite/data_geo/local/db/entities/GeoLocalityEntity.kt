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
import com.oborodulin.jwsuite.domain.types.LocalityType
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoLocalityEntity.TABLE_NAME,
    indices = [Index(value = ["lRegionsId", "lRegionDistrictsId", "localityCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoRegionEntity::class,
        parentColumns = arrayOf("regionId"),
        childColumns = arrayOf("lRegionsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoRegionDistrictEntity::class,
        parentColumns = arrayOf("regionDistrictId"),
        childColumns = arrayOf("lRegionDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoLocalityEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val localityId: UUID = UUID.randomUUID(),
    val localityCode: String,
    val localityType: LocalityType,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val lRegionDistrictsId: UUID? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val lRegionsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_localities"

        fun defaultLocality(
            localityId: UUID = UUID.randomUUID(), regionId: UUID = UUID.randomUUID(),
            districtId: UUID? = null,
            localityCode: String, localityType: LocalityType
        ) = GeoLocalityEntity(
            localityId = localityId, lRegionsId = regionId, lRegionDistrictsId = districtId,
            localityCode = localityCode, localityType = localityType
        )

        fun donetskLocality(ctx: Context, regionId: UUID) = defaultLocality(
            regionId = regionId,
            localityCode = ctx.resources.getString(R.string.def_donetsk_code),
            localityType = LocalityType.CITY
        )

        fun makeevkaLocality(ctx: Context, regionId: UUID) = defaultLocality(
            regionId = regionId,
            localityCode = ctx.resources.getString(R.string.def_makeevka_code),
            localityType = LocalityType.CITY
        )

        fun luganskLocality(ctx: Context, regionId: UUID) = defaultLocality(
            regionId = regionId,
            localityCode = ctx.resources.getString(R.string.def_luhansk_code),
            localityType = LocalityType.CITY
        )

        fun marinkaLocality(ctx: Context, regionId: UUID, districtId: UUID) = defaultLocality(
            regionId = regionId, districtId = districtId,
            localityCode = ctx.resources.getString(R.string.def_marinka_code),
            localityType = LocalityType.CITY
        )

        fun mospinoLocality(ctx: Context, regionId: UUID, districtId: UUID) = defaultLocality(
            regionId = regionId, districtId = districtId,
            localityCode = ctx.resources.getString(R.string.def_mospino_code),
            localityType = LocalityType.CITY
        )
    }

    override fun id() = this.localityId

    override fun key(): Int {
        var result = lRegionsId.hashCode()
        result = result * 31 + localityCode.hashCode()
        result = result * 31 + lRegionDistrictsId.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Locality Entity ").append(localityType)
            .append(" '").append(localityCode).append("'")
        lRegionDistrictsId?.let { str.append(" [lRegionDistrictsId = ").append(it).append("]") }
        str.append(" localityId = ").append(localityId)
        return str.toString()
    }
}