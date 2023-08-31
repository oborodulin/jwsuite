package com.oborodulin.jwsuite.data_territory.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import java.util.UUID

@Entity(
    tableName = TerritoryStreetEntity.TABLE_NAME,
    indices = [Index(
        value = ["tsTerritoriesId", "tsStreetsId", "isEvenSide", "isTerStreetPrivateSector"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("tsTerritoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoStreetEntity::class,
        parentColumns = arrayOf("streetId"),
        childColumns = arrayOf("tsStreetsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class TerritoryStreetEntity(
    @PrimaryKey val territoryStreetId: UUID = UUID.randomUUID(),
    val isEvenSide: Boolean? = null,
    val isTerStreetPrivateSector: Boolean? = null,
    val estTerStreetHouses: Int? = null, // estimated houses of the territory street
    @ColumnInfo(index = true) val tsStreetsId: UUID,
    @ColumnInfo(index = true) val tsTerritoriesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territory_streets"

        fun defaultTerritoryStreet(
            territoryId: UUID = UUID.randomUUID(), territoryStreetId: UUID = UUID.randomUUID(),
            streetId: UUID = UUID.randomUUID(),
            isEven: Boolean? = null, isPrivateSector: Boolean? = null, estimatedHouses: Int? = null
        ) = TerritoryStreetEntity(
            tsTerritoriesId = territoryId,
            territoryStreetId = territoryStreetId,
            tsStreetsId = streetId,
            isEvenSide = isEven,
            isTerStreetPrivateSector = isPrivateSector,
            estTerStreetHouses = estimatedHouses
        )

        fun evenTerritoryStreet(
            territoryId: UUID, streetId: UUID, isPrivateSector: Boolean? = null,
            estimatedHouses: Int? = null
        ) = defaultTerritoryStreet(
            territoryId = territoryId, streetId = streetId, isEven = true,
            isPrivateSector = isPrivateSector, estimatedHouses = estimatedHouses
        )

        fun oddTerritoryStreet(
            territoryId: UUID, streetId: UUID, isPrivateSector: Boolean? = null,
            estimatedHouses: Int? = null
        ) = defaultTerritoryStreet(
            territoryId = territoryId, streetId = streetId, isEven = false,
            isPrivateSector = isPrivateSector, estimatedHouses = estimatedHouses
        )

        fun privateSectorTerritoryStreet(
            territoryId: UUID, streetId: UUID, isEven: Boolean? = null, estimatedHouses: Int? = null
        ) = defaultTerritoryStreet(
            territoryId = territoryId, streetId = streetId,
            isEven = isEven, isPrivateSector = true, estimatedHouses = estimatedHouses
        )

    }

    override fun id() = this.territoryStreetId

    override fun key(): Int {
        var result = tsTerritoriesId.hashCode()
        result = result * 31 + tsStreetsId.hashCode()
        result = result * 31 + isEvenSide.hashCode()
        result = result * 31 + isTerStreetPrivateSector.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Street Entity ").append(" [territoriesId = ").append(tsTerritoriesId)
            .append("; tsStreetsId = ").append(tsStreetsId)
            .append("; isEvenSide = ").append(isEvenSide)
            .append("; isTerStreetPrivateSector = ").append(isTerStreetPrivateSector)
            .append("] territoryStreetId = ").append(territoryStreetId)
        return str.toString()
    }
}