package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = RoomEntity.TABLE_NAME,
    indices = [Index(value = ["rHousesId", "roomNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"), childColumns = arrayOf("rHousesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = EntranceEntity::class,
        parentColumns = arrayOf("entranceId"), childColumns = arrayOf("rEntrancesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = FloorEntity::class,
        parentColumns = arrayOf("floorId"), childColumns = arrayOf("rFloorsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("rTerritoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class RoomEntity(
    @PrimaryKey val roomId: UUID = UUID.randomUUID(),
    val roomNum: Int,
    val isIntercomRoom: Boolean? = null,
    val isResidentialRoom: Boolean = true,
    val isForeignLangRoom: Boolean = false,
    val roomDesc: String? = null,
    @ColumnInfo(index = true) val rTerritoriesId: UUID? = null,
    @ColumnInfo(index = true) val rFloorsId: UUID? = null,
    @ColumnInfo(index = true) val rEntrancesId: UUID? = null,
    @ColumnInfo(index = true) val rHousesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "rooms"

        fun defaultRoom(
            houseId: UUID = UUID.randomUUID(), entrancesId: UUID? = null,
            roomId: UUID = UUID.randomUUID(),
            roomNum: Int, isIntercom: Boolean? = null, isResidential: Boolean = true,
            isForeignLanguage: Boolean = false
        ) = RoomEntity(
            rHousesId = houseId, rEntrancesId = entrancesId, roomId = roomId,
            roomNum = roomNum, isIntercomRoom = isIntercom, isResidentialRoom = isResidential,
            isForeignLangRoom = isForeignLanguage
        )

    }

    override fun id() = this.roomId

    override fun key(): Int {
        var result = rHousesId.hashCode()
        result = result * 31 + roomNum.hashCode()
        rEntrancesId?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Room Entity №").append(roomNum).append(" [housesId = ").append(rHousesId)
        rEntrancesId?.let { str.append("; entrancesId = ").append(it) }
        str.append("; isIntercom = ").append(isIntercomRoom)
            .append("; isResidential = ").append(isResidentialRoom)
            .append(" [housesId = ").append(rHousesId)
        return str.toString()
    }
}