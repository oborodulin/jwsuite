package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = HouseEntity.TABLE_NAME,
    indices = [Index(value = ["streetsId", "zipCode", "houseNum", "buildingNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoStreetEntity::class,
        parentColumns = arrayOf("streetId"),
        childColumns = arrayOf("streetsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoMicrodistrictEntity::class,
        parentColumns = arrayOf("microdistrictId"), childColumns = arrayOf("microdistrictsId"),
        onDelete = ForeignKey.CASCADE, deferred = true
    ), ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("localityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"), childColumns = arrayOf("territoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class HouseEntity(
    @PrimaryKey val houseId: UUID = UUID.randomUUID(),
    val zipCode: String? = null,
    val houseNum: Int,
    val buildingNum: String? = null,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val entrancesQty: Int? = null, // number of entrances of the house
    val floorsQty: Int? = null, // number of floors of the house
    val roomsByFloor: Int? = null, // number rooms by one floor of the house
    val estimatedRooms: Int? = null, // estimated rooms of the house (if null then entrancesQty * floorsQty * roomsByFloor)
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val isHostel: Boolean = false,
    val territoryDesc: String? = null,
    @ColumnInfo(index = true) val territoriesId: UUID? = null,
    @ColumnInfo(index = true) val microdistrictsId: UUID? = null,
    @ColumnInfo(index = true) val localityDistrictsId: UUID? = null,
    @ColumnInfo(index = true) val streetsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "houses"

        fun defaultHouse(
            streetId: UUID = UUID.randomUUID(), microdistrictId: UUID? = null,
            localityDistrictId: UUID? = null, houseId: UUID = UUID.randomUUID(),
            zipCode: String? = null, houseNum: Int, buildingNum: String? = null,
            isBusiness: Boolean = false,
            entrancesQty: Int? = null, floorsQty: Int? = null, roomsByFloor: Int? = null,
            estimatedRooms: Int? = null, isForeignLanguage: Boolean = false,
            isPrivateSector: Boolean = false, isHostel: Boolean = false,
            territoryDesc: String? = null
        ) = HouseEntity(
            streetsId = streetId, localityDistrictsId = localityDistrictId,
            microdistrictsId = microdistrictId,
            houseId = houseId,
            zipCode = zipCode, houseNum = houseNum, buildingNum = buildingNum,
            isBusiness = isBusiness,
            entrancesQty = entrancesQty, floorsQty = floorsQty, roomsByFloor = roomsByFloor,
            estimatedRooms = estimatedRooms, isForeignLanguage = isForeignLanguage,
            isPrivateSector = isPrivateSector, isHostel = isHostel,
            territoryDesc = territoryDesc
        )

    }

    override fun id() = this.houseId

    override fun key(): Int {
        var result = streetsId.hashCode()
        result = result * 31 + houseNum.hashCode()
        zipCode?.let { result = result * 31 + it.hashCode() }
        buildingNum?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("House Entity №").append(houseNum)
        buildingNum?.let { str.append(" - ").append(it) }
        zipCode?.let { str.append(". ZIP Code: ").append(it) }
        str.append(" [streetId = ").append(streetsId).append("] houseId = ").append(houseId)
        return str.toString()
    }
}