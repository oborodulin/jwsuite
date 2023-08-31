package com.oborodulin.jwsuite.data_territory.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = FloorEntity.TABLE_NAME,
    indices = [Index(value = ["fHousesId", "fEntrancesId", "floorNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = EntranceEntity::class,
        parentColumns = arrayOf("entranceId"),
        childColumns = arrayOf("fEntrancesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("fHousesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("fTerritoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class FloorEntity(
    @PrimaryKey val floorId: UUID = UUID.randomUUID(),
    val floorNum: Int,
    val isSecurityFloor: Boolean = false,
    val isIntercomFloor: Boolean? = null,
    val isResidentialFloor: Boolean = true,
    val roomsByFloor: Int? = null, // number rooms by one floor of the entrance
    val estFloorRooms: Int? = null, // estimated rooms of the house (if null then roomsByFloor)
    val floorDesc: String? = null,
    @ColumnInfo(index = true) val fTerritoriesId: UUID? = null,
    @ColumnInfo(index = true) val fEntrancesId: UUID? = null,
    @ColumnInfo(index = true) val fHousesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "floors"

        fun defaultFloor(
            houseId: UUID = UUID.randomUUID(), entranceId: UUID? = null,
            territoryId: UUID? = null, floorId: UUID = UUID.randomUUID(),
            floorNum: Int,
            isSecurity: Boolean = false, isIntercom: Boolean? = null,
            isResidential: Boolean, roomsByFloor: Int? = null,
            estimatedRooms: Int? = null, territoryDesc: String? = null
        ) = FloorEntity(
            fHousesId = houseId, fEntrancesId = entranceId, fTerritoriesId = territoryId,
            floorId = floorId,
            floorNum = floorNum, isSecurityFloor = isSecurity, isIntercomFloor = isIntercom,
            isResidentialFloor = isResidential, roomsByFloor = roomsByFloor,
            estFloorRooms = estimatedRooms,
            floorDesc = territoryDesc
        )

    }

    override fun id() = this.floorId

    override fun key(): Int {
        var result = fHousesId.hashCode()
        result = result * 31 + floorNum.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("House Floor Entity №").append(floorNum)
        str.append(" [fHousesId = ").append(fHousesId)
            .append("; fEntrancesId = ").append(fEntrancesId)
            .append("; fTerritoriesId = ").append(fTerritoriesId)
            .append("] floorId = ").append(floorId)
        return str.toString()
    }
}