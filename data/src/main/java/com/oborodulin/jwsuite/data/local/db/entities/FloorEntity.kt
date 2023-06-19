package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = FloorEntity.TABLE_NAME,
    indices = [Index(value = ["housesId", "entrancesId", "floorNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = EntranceEntity::class,
        parentColumns = arrayOf("entranceId"),
        childColumns = arrayOf("entrancesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
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
data class FloorEntity(
    @PrimaryKey val floorId: UUID = UUID.randomUUID(),
    val floorNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val roomsByFloor: Int? = null, // number rooms by one floor of the entrance
    val estimatedRooms: Int? = null, // estimated rooms of the house (if null then roomsByFloor)
    val territoryDesc: String? = null,
    @ColumnInfo(index = true) val territoriesId: UUID? = null,
    @ColumnInfo(index = true) val entrancesId: UUID? = null,
    @ColumnInfo(index = true) val housesId: UUID
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
            housesId = houseId, entrancesId = entranceId, territoriesId = territoryId,
            floorId = floorId,
            floorNum = floorNum, isSecurity = isSecurity, isIntercom = isIntercom,
            isResidential = isResidential, roomsByFloor = roomsByFloor,
            estimatedRooms = estimatedRooms,
            territoryDesc = territoryDesc
        )

    }

    override fun id() = this.floorId

    override fun key(): Int {
        var result = housesId.hashCode()
        result = result * 31 + floorNum.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("House Floor Entity №").append(floorNum)
        str.append(" [houseId = ").append(housesId)
            .append("; entrancesId = ").append(entrancesId)
            .append("; territoriesId = ").append(territoriesId)
            .append("] floorId = ").append(floorId)
        return str.toString()
    }
}