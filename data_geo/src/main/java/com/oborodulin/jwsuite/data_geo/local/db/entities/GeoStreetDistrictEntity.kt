package com.oborodulin.jwsuite.data_geo.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoStreetDistrictEntity.TABLE_NAME,
    indices = [Index(
        value = ["dsStreetsId", "dsLocalityDistrictsId", "dsMicrodistrictsId"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoMicrodistrictEntity::class,
        parentColumns = arrayOf("microdistrictId"), childColumns = arrayOf("dsMicrodistrictsId"),
        onDelete = ForeignKey.CASCADE, deferred = true
    ), ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("dsLocalityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoStreetEntity::class,
        parentColumns = arrayOf("streetId"), childColumns = arrayOf("dsStreetsId"),
        onDelete = ForeignKey.CASCADE, deferred = true
    )]
)
@Serializable
data class GeoStreetDistrictEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val streetDistrictId: UUID = UUID.randomUUID(),
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val dsStreetsId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val dsLocalityDistrictsId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val dsMicrodistrictsId: UUID? = null
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_street_districts"

        fun defaultDistrictStreet(
            streetDistrictId: UUID = UUID.randomUUID(), streetId: UUID = UUID.randomUUID(),
            localityDistrictId: UUID = UUID.randomUUID(), microdistrictId: UUID? = null
        ) = GeoStreetDistrictEntity(
            streetDistrictId = streetDistrictId, dsStreetsId = streetId,
            dsLocalityDistrictsId = localityDistrictId, dsMicrodistrictsId = microdistrictId
        )

    }

    override fun id() = this.streetDistrictId

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Street District Entity")
            .append(" [dsStreetsId = ").append(dsStreetsId)
            .append("; dsLocalityDistrictsId = ").append(dsLocalityDistrictsId)
        dsMicrodistrictsId?.let { str.append("; dsMicrodistrictsId = ").append(it) }
        str.append("] streetDistrictId = ").append(streetDistrictId)
        return str.toString()
    }
}