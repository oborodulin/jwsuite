package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = TerritoryStreetEntity.TABLE_NAME,
    indices = [Index(
        value = ["territoriesId", "streetsId", "isEven", "isPrivateSector"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("territoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoStreetEntity::class,
        parentColumns = arrayOf("streetId"),
        childColumns = arrayOf("streetsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class TerritoryStreetEntity(
    @PrimaryKey val territoryStreetId: UUID = UUID.randomUUID(),
    val isEven: Boolean? = null,
    val isPrivateSector: Boolean? = null,
    val estimatedHouses: Int? = null, // estimated houses of the territory street
    @ColumnInfo(index = true) val streetsId: UUID,
    @ColumnInfo(index = true) val territoriesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territory_streets"

        fun defaultTerritoryStreet(
            territoryId: UUID = UUID.randomUUID(), territoryStreetId: UUID = UUID.randomUUID(),
            streetId: UUID = UUID.randomUUID(),
            isEven: Boolean? = null, isPrivateSector: Boolean? = null, estimatedHouses: Int? = null
        ) = TerritoryStreetEntity(
            territoriesId = territoryId, territoryStreetId = territoryStreetId,
            streetsId = streetId,
            isEven = isEven, isPrivateSector = isPrivateSector, estimatedHouses = estimatedHouses
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
        var result = territoriesId.hashCode()
        result = result * 31 + streetsId.hashCode()
        isEven?.let { result = result * 31 + it.hashCode() }
        isPrivateSector?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Street Entity ").append(" [territoriesId = ").append(territoriesId)
            .append("; streetsId = ").append(streetsId)
            .append("; isEven = ").append(isEven)
            .append("; isPrivateSector = ").append(isPrivateSector)
            .append("] territoryStreetId = ").append(territoryStreetId)
        return str.toString()
    }
}