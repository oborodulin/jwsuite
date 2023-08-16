package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import java.util.UUID

@Entity(
    tableName = GeoLocalityDistrictEntity.TABLE_NAME,
    indices = [Index(value = ["ldLocalitiesId", "locDistrictShortName"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("ldLocalitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class GeoLocalityDistrictEntity(
    @PrimaryKey val localityDistrictId: UUID = UUID.randomUUID(),
    val locDistrictShortName: String,
    @ColumnInfo(index = true) val ldLocalitiesId: UUID,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_locality_districts"

        fun defaultLocalityDistrict(
            localityDistrictId: UUID = UUID.randomUUID(), localityId: UUID = UUID.randomUUID(),
            districtShortName: String
        ) = GeoLocalityDistrictEntity(
            ldLocalitiesId = localityId, localityDistrictId = localityDistrictId,
            locDistrictShortName = districtShortName
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
        result = result * 31 + locDistrictShortName.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Locality District Entity '").append(locDistrictShortName).append("' ")
            .append(" [ldLocalitiesId = ").append(ldLocalitiesId)
            .append("] localityDistrictId = ").append(localityDistrictId)
        return str.toString()
    }
}