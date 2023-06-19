package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = EntranceEntity.TABLE_NAME,
    indices = [Index(value = ["housesId", "entranceNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("housesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("territoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class EntranceEntity(
    @PrimaryKey val entranceId: UUID = UUID.randomUUID(),
    val entranceNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val floorsQty: Int? = null, // number of floors of the entrance
    val roomsByFloor: Int? = null, // number rooms by one floor of the entrance
    val estimatedRooms: Int? = null, // estimated rooms of the house (if null then floorsQty * roomsByFloor)
    val territoryDesc: String? = null,
    @ColumnInfo(index = true) val territoriesId: UUID? = null,
    @ColumnInfo(index = true) val housesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "entrances"

        fun defaultEntrance(
            houseId: UUID = UUID.randomUUID(), entranceId: UUID = UUID.randomUUID(),
            territoryId: UUID? = null, entranceNum: Int,
            isSecurity: Boolean = false, isIntercom: Boolean? = null,
            isResidential: Boolean, floorsQty: Int? = null, roomsByFloor: Int? = null,
            estimatedRooms: Int? = null, territoryDesc: String? = null
        ) = EntranceEntity(
            housesId = houseId, territoriesId = territoryId, entranceId = entranceId,
            entranceNum = entranceNum, isSecurity = isSecurity, isIntercom = isIntercom,
            isResidential = isResidential,
            floorsQty = floorsQty, roomsByFloor = roomsByFloor,
            estimatedRooms = estimatedRooms,
            territoryDesc = territoryDesc
        )

    }

    override fun id() = this.entranceId

    override fun key(): Int {
        var result = housesId.hashCode()
        result = result * 31 + entranceNum.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Entrance Entity №").append(entranceNum)
        str.append(" [houseId = ").append(housesId)
            .append("; territoriesId = ").append(territoriesId)
            .append("] entranceId = ").append(entranceId)
        return str.toString()
    }
}