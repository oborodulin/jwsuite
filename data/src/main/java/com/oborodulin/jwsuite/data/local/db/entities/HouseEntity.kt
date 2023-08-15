package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.domain.util.BuildingType
import java.util.UUID

@Entity(
    tableName = HouseEntity.TABLE_NAME,
    indices = [Index(
        value = ["hStreetsId", "zipCode", "houseNum", "houseLetter", "buildingNum"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoStreetEntity::class,
        parentColumns = arrayOf("streetId"),
        childColumns = arrayOf("hStreetsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoMicrodistrictEntity::class,
        parentColumns = arrayOf("microdistrictId"), childColumns = arrayOf("hMicrodistrictsId"),
        onDelete = ForeignKey.CASCADE, deferred = true
    ), ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("hLocalityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"), childColumns = arrayOf("hTerritoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class HouseEntity(
    @PrimaryKey val houseId: UUID = UUID.randomUUID(),
    val zipCode: String? = null,
    val houseNum: Int,
    val houseLetter: String? = null,
    val buildingNum: Int? = null,
    val buildingType: BuildingType = BuildingType.HOUSE,
    val isBusinessHouse: Boolean = false,
    val isSecurityHouse: Boolean = false,
    val isIntercomHouse: Boolean? = null,
    val isResidentialHouse: Boolean = true,
    val houseEntrancesQty: Int? = null, // number of entrances of the house
    val floorsByEntrance: Int? = null, // number of floors of the entrance
    val roomsByHouseFloor: Int? = null, // number rooms by one floor and one entrance of the house
    val estHouseRooms: Int? = null, // estimated rooms of the house (if null then houseEntrancesQty * floorsByEntrance * roomsByHouseFloor)
    val isForeignLangHouse: Boolean = false,
    val isHousePrivateSector: Boolean = false,
    val houseDesc: String? = null,
    @ColumnInfo(index = true) val hTerritoriesId: UUID? = null,
    @ColumnInfo(index = true) val hMicrodistrictsId: UUID? = null,
    @ColumnInfo(index = true) val hLocalityDistrictsId: UUID? = null,
    @ColumnInfo(index = true) val hStreetsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "houses"

        fun defaultHouse(
            streetId: UUID = UUID.randomUUID(), microdistrictId: UUID? = null,
            localityDistrictId: UUID? = null, houseId: UUID = UUID.randomUUID(),
            zipCode: String? = null, houseNum: Int, houseLetter: String? = null,
            buildingNum: Int? = null, isBusiness: Boolean = false,
            entrancesQty: Int? = null, floorsByEntrance: Int? = null, roomsByFloor: Int? = null,
            estimatedRooms: Int? = null, isForeignLanguage: Boolean = false,
            isPrivateSector: Boolean = false, buildingType: BuildingType = BuildingType.HOUSE,
            territoryDesc: String? = null
        ) = HouseEntity(
            hStreetsId = streetId, hLocalityDistrictsId = localityDistrictId,
            hMicrodistrictsId = microdistrictId,
            houseId = houseId,
            zipCode = zipCode, houseNum = houseNum, houseLetter = houseLetter,
            buildingNum = buildingNum,
            isBusinessHouse = isBusiness, houseEntrancesQty = entrancesQty,
            floorsByEntrance = floorsByEntrance, roomsByHouseFloor = roomsByFloor,
            estHouseRooms = estimatedRooms,
            isForeignLangHouse = isForeignLanguage, isHousePrivateSector = isPrivateSector,
            buildingType = buildingType, houseDesc = territoryDesc
        )

    }

    override fun id() = this.houseId

    override fun key(): Int {
        var result = hStreetsId.hashCode()
        result = result * 31 + houseNum.hashCode()
        result = result * 31 + zipCode.hashCode()
        result = result * 31 + houseLetter.hashCode()
        result = result * 31 + buildingNum.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("House Entity ").append(buildingType).append(" №").append(houseNum)
        houseLetter?.let { str.append(it) }
        buildingNum?.let { str.append("-").append(it) }
        zipCode?.let { str.append(". ZIP Code: ").append(it) }
        str.append(" [hStreetsId = ").append(hStreetsId)
            .append("] houseId = ").append(houseId)
        return str.toString()
    }
}