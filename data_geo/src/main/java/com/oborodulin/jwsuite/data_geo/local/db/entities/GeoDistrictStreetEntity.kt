package com.oborodulin.jwsuite.data_geo.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = GeoDistrictStreetEntity.TABLE_NAME,
    indices = [Index(
        value = ["dsLocalityDistrictsId", "dsMicrodistrictsId", "dsStreetsId"],
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
data class GeoDistrictStreetEntity(
    @PrimaryKey val districtStreetId: UUID = UUID.randomUUID(),
    @ColumnInfo(index = true) val dsMicrodistrictsId: UUID? = null,
    @ColumnInfo(index = true) val dsLocalityDistrictsId: UUID,
    @ColumnInfo(index = true) val dsStreetsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_district_streets"

        fun defaultDistrictStreet(
            districtStreetId: UUID = UUID.randomUUID(), streetId: UUID = UUID.randomUUID(),
            localityDistrictId: UUID = UUID.randomUUID(), microdistrictId: UUID? = null
        ) = GeoDistrictStreetEntity(
            districtStreetId = districtStreetId, dsStreetsId = streetId,
            dsLocalityDistrictsId = localityDistrictId, dsMicrodistrictsId = microdistrictId
        )

    }

    override fun id() = this.districtStreetId

    override fun toString(): String {
        val str = StringBuffer()
        str.append("District Street Entity ")
            .append(" [dsStreetsId = ").append(dsStreetsId)
            .append("; dsLocalityDistrictsId = ").append(dsLocalityDistrictsId)
        dsMicrodistrictsId?.let { str.append("; microdistrictsId = ").append(it) }
        str.append("] districtStreetId = ").append(districtStreetId)
        return str.toString()
    }
}