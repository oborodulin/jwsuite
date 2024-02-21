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
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoLocalityDistrictEntity.TABLE_NAME,
    indices = [Index(
        value = ["ldLocalitiesId", "locDistrictOsmId", "locDistrictShortName"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("ldLocalitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoLocalityDistrictEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val localityDistrictId: UUID = UUID.randomUUID(),
    val locDistrictShortName: String,
    @ColumnInfo(index = true) val locDistrictOsmId: Long? = null,
    @Embedded(prefix = PREFIX) val coordinates: Coordinates? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val ldLocalitiesId: UUID,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_locality_districts"
        const val PREFIX = "locDistrict_"

        fun defaultLocalityDistrict(
            localityDistrictId: UUID = UUID.randomUUID(), localityId: UUID = UUID.randomUUID(),
            districtShortName: String,
            locDistrictOsmId: Long? = null, coordinates: Coordinates? = null
        ) = GeoLocalityDistrictEntity(
            ldLocalitiesId = localityId, localityDistrictId = localityDistrictId,
            locDistrictShortName = districtShortName,
            locDistrictOsmId = locDistrictOsmId, coordinates = coordinates
        )

        fun bdnLocalityDistrict(ctx: Context, localityId: UUID) = defaultLocalityDistrict(
            districtShortName = ctx.resources.getString(R.string.def_budyonovsky_short_name),
            localityId = localityId
        )

        fun kvkLocalityDistrict(ctx: Context, localityId: UUID) = defaultLocalityDistrict(
            districtShortName = ctx.resources.getString(R.string.def_kievsky_short_name),
            localityId = localityId
        )

        fun kbshLocalityDistrict(ctx: Context, localityId: UUID) = defaultLocalityDistrict(
            districtShortName = ctx.resources.getString(R.string.def_kuybyshevsky_short_name),
            localityId = localityId
        )

        fun vrshLocalityDistrict(ctx: Context, localityId: UUID) = defaultLocalityDistrict(
            districtShortName = ctx.resources.getString(R.string.def_voroshilovsky_short_name),
            localityId = localityId
        )
    }

    override fun id() = this.localityDistrictId

    override fun key(): Int {
        var result = ldLocalitiesId.hashCode()
        result = result * 31 + locDistrictOsmId.hashCode()
        result = result * 31 + locDistrictShortName.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Locality District Entity '").append(locDistrictShortName)
            .append("'. OSM: locDistrictOsmId = ").append(locDistrictOsmId)
            .append("; coordinates = ").append(coordinates)
            .append(" [ldLocalitiesId = ").append(ldLocalitiesId)
            .append("] localityDistrictId = ").append(localityDistrictId)
        return str.toString()
    }
}