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
    indices = [Index(value = ["housesId", "roomNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"), childColumns = arrayOf("housesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = EntranceEntity::class,
        parentColumns = arrayOf("entranceId"), childColumns = arrayOf("entrancesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = FloorEntity::class,
        parentColumns = arrayOf("floorId"), childColumns = arrayOf("floorsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class RoomEntity(
    @PrimaryKey val roomId: UUID = UUID.randomUUID(),
    val roomNum: Int,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val territoryDesc: String? = null,
    @ColumnInfo(index = true) val territoriesId: UUID? = null,
    @ColumnInfo(index = true) val floorsId: UUID? = null,
    @ColumnInfo(index = true) val entrancesId: UUID? = null,
    @ColumnInfo(index = true) val housesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "rooms"

        fun defaultRoom(
            houseId: UUID = UUID.randomUUID(), entrancesId: UUID? = null,
            roomId: UUID = UUID.randomUUID(),
            roomNum: Int, isIntercom: Boolean? = null, isResidential: Boolean = true,
            isForeignLanguage: Boolean = false
        ) = RoomEntity(
            housesId = houseId, entrancesId = entrancesId, roomId = roomId,
            roomNum = roomNum, isIntercom = isIntercom, isResidential = isResidential,
            isForeignLanguage = isForeignLanguage
        )

    }

    override fun id() = this.roomId

    override fun key(): Int {
        var result = housesId.hashCode()
        result = result * 31 + roomNum.hashCode()
        entrancesId?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Room Entity №").append(roomNum).append(" [housesId = ").append(housesId)
        entrancesId?.let { str.append("; entrancesId = ").append(it) }
        str.append("; isIntercom = ").append(isIntercom)
            .append("; isResidential = ").append(isResidential)
            .append(" [housesId = ").append(housesId)
        return str.toString()
    }
}