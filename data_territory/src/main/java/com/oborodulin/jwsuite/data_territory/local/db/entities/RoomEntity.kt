package com.oborodulin.jwsuite.data_territory.local.db.entities

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
@Serializable
data class RoomEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val roomId: UUID = UUID.randomUUID(),
    val roomNum: Int,
    val isIntercomRoom: Boolean? = null,
    val isResidentialRoom: Boolean = true,
    val isForeignLangRoom: Boolean = false,
    val roomDesc: String? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val rTerritoriesId: UUID? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val rFloorsId: UUID? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val rEntrancesId: UUID? = null,
    @Serializable(with = UUIDSerializer::class)
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
        result = result * 31 + rEntrancesId.hashCode()
        result = result * 31 + rFloorsId.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Room Entity №").append(roomNum).append(" [rHousesId = ").append(rHousesId)
        rEntrancesId?.let { str.append("; rEntrancesId = ").append(it) }
        rFloorsId?.let { str.append("; rFloorsId = ").append(it) }
        rTerritoriesId?.let { str.append("; rTerritoriesId = ").append(it) }
        str.append("; isIntercom = ").append(isIntercomRoom)
            .append("; isResidential = ").append(isResidentialRoom)
            .append("] roomId = ").append(roomId)
        return str.toString()
    }
}