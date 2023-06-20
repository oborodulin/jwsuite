package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.util.UUID

@Entity(
    tableName = TerritoryEntity.TABLE_NAME,
    indices = [Index(
        value = ["congregationsId", "territoryCategoriesId", "localitiesId", "localityDistrictsId", "microdistrictsId", "territoryNum"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
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
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("localitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryCategoryEntity::class,
        parentColumns = arrayOf("territoryCategoryId"),
        childColumns = arrayOf("territoryCategoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("congregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class TerritoryEntity(
    @PrimaryKey val territoryId: UUID = UUID.randomUUID(),
    val territoryNum: Int,
    val isActive: Boolean = true,
    val isBusiness: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false,
    val territoryDesc: String? = null,
    @ColumnInfo(index = true) val microdistrictsId: UUID? = null,
    @ColumnInfo(index = true) val localityDistrictsId: UUID? = null,
    @ColumnInfo(index = true) val localitiesId: UUID,
    @ColumnInfo(index = true) val territoryCategoriesId: UUID,
    @ColumnInfo(index = true) val congregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territories"

        fun defaultTerritory(
            territoryId: UUID = UUID.randomUUID(),
            congregationId: UUID = UUID.randomUUID(), territoryCategoryId: UUID = UUID.randomUUID(),
            localityId: UUID = UUID.randomUUID(), localityDistrictId: UUID? = null,
            microdistrictId: UUID? = null,
            territoryNum: Int, isBusiness: Boolean = false, isInPerimeter: Boolean = false,
            isProcessed: Boolean = false, isActive: Boolean = true, territoryDesc: String? = null
        ) = TerritoryEntity(
            congregationsId = congregationId, territoryCategoriesId = territoryCategoryId,
            localitiesId = localityId, localityDistrictsId = localityDistrictId,
            microdistrictsId = microdistrictId,
            territoryId = territoryId,
            territoryNum = territoryNum, isBusiness = isBusiness, isInPerimeter = isInPerimeter,
            isProcessed = isProcessed, isActive = isActive, territoryDesc = territoryDesc
        )

        fun businessTerritory(
            congregationId: UUID = UUID.randomUUID(), territoryCategoryId: UUID = UUID.randomUUID(),
            localityId: UUID = UUID.randomUUID(), localityDistrictId: UUID? = null,
            microdistrictId: UUID? = null,
            territoryNum: Int, territoryDesc: String? = null
        ) = defaultTerritory(
            congregationId = congregationId, territoryCategoryId = territoryCategoryId,
            localityId = localityId, localityDistrictId = localityDistrictId,
            microdistrictId = microdistrictId,
            territoryNum = territoryNum, isBusiness = true,
            territoryDesc = territoryDesc
        )
    }

    override fun id() = this.territoryId

    override fun key(): Int {
        var result = congregationsId.hashCode()
        result = result * 31 + territoryCategoriesId.hashCode()
        result = result * 31 + localitiesId.hashCode()
        result = result * 31 + territoryNum.hashCode()
        localityDistrictsId?.let { result = result * 31 + it.hashCode() }
        microdistrictsId?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Entity №").append(territoryNum)
            .append(" [congregationsId = ").append(congregationsId)
            .append("; isActive = ").append(isActive)
            .append("; isBusiness = ").append(isBusiness)
            .append("; isGroupMinistry = ").append(isGroupMinistry)
            .append("; isInPerimeter = ").append(isInPerimeter)
            .append("; isProcessed = ").append(isProcessed)
        localityDistrictsId?.let { str.append("; localityDistrictsId = ").append(it) }
        microdistrictsId?.let { str.append("; microdistrictsId = ").append(it) }
        str.append("; localitiesId = ").append(localitiesId)
            .append("; territoryCategoriesId = ").append(territoryCategoriesId)
            .append("] territoryId = ").append(territoryId)
        return str.toString()
    }
}