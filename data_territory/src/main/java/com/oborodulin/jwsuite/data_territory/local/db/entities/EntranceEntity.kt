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
    tableName = EntranceEntity.TABLE_NAME,
    indices = [Index(value = ["eHousesId", "entranceNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("eHousesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("eTerritoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class EntranceEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val entranceId: UUID = UUID.randomUUID(),
    val entranceNum: Int,
    val isSecurityEntrance: Boolean = false,
    val isIntercomEntrance: Boolean? = null,
    val isResidentialEntrance: Boolean = true,
    val entranceFloorsQty: Int? = null, // number of floors of the entrance
    val roomsByEntranceFloor: Int? = null, // number rooms by one floor of the entrance
    val estEntranceRooms: Int? = null, // estimated rooms of the house (if null then entranceFloorsQty * roomsByEntranceFloor)
    val entranceDesc: String? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val eTerritoriesId: UUID? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val eHousesId: UUID
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
            eHousesId = houseId,
            eTerritoriesId = territoryId,
            entranceId = entranceId,
            entranceNum = entranceNum,
            isSecurityEntrance = isSecurity,
            isIntercomEntrance = isIntercom,
            isResidentialEntrance = isResidential,
            entranceFloorsQty = floorsQty,
            roomsByEntranceFloor = roomsByFloor,
            estEntranceRooms = estimatedRooms,
            entranceDesc = territoryDesc
        )

    }

    override fun id() = this.entranceId

    override fun key(): Int {
        var result = eHousesId.hashCode()
        result = result * 31 + entranceNum.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Entrance Entity №").append(entranceNum)
        str.append(" [eHousesId = ").append(eHousesId)
            .append("; eTerritoriesId = ").append(eTerritoriesId)
            .append("] entranceId = ").append(entranceId)
        return str.toString()
    }
}