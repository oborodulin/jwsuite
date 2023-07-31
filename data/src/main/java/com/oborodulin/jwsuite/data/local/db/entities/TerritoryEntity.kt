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
        value = ["tCongregationsId", "tTerritoryCategoriesId", "tLocalitiesId", "tLocalityDistrictsId", "tMicrodistrictsId", "territoryNum"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoMicrodistrictEntity::class,
        parentColumns = arrayOf("microdistrictId"), childColumns = arrayOf("tMicrodistrictsId"),
        onDelete = ForeignKey.CASCADE, deferred = true
    ), ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("tLocalityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"), childColumns = arrayOf("tLocalitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryCategoryEntity::class,
        parentColumns = arrayOf("territoryCategoryId"),
        childColumns = arrayOf("tTerritoryCategoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"), childColumns = arrayOf("tCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class TerritoryEntity(
    @PrimaryKey val territoryId: UUID = UUID.randomUUID(),
    val territoryNum: Int,
    val isActive: Boolean = true,
    val isBusinessTerritory: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false, // for isGroupMinistry
    val territoryDesc: String? = null,
    @ColumnInfo(index = true) val tMicrodistrictsId: UUID? = null,
    @ColumnInfo(index = true) val tLocalityDistrictsId: UUID? = null,
    @ColumnInfo(index = true) val tLocalitiesId: UUID,
    @ColumnInfo(index = true) val tTerritoryCategoriesId: UUID,
    @ColumnInfo(index = true) val tCongregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territories"

        fun defaultTerritory(
            territoryId: UUID = UUID.randomUUID(),
            congregationId: UUID = UUID.randomUUID(), territoryCategoryId: UUID = UUID.randomUUID(),
            localityId: UUID = UUID.randomUUID(), localityDistrictId: UUID? = null,
            microdistrictId: UUID? = null,
            territoryNum: Int,
            isBusiness: Boolean = false, isInPerimeter: Boolean = false,
            isGroupMinistry: Boolean = false,
            isProcessed: Boolean = false, isActive: Boolean = true, territoryDesc: String? = null
        ) = TerritoryEntity(
            tCongregationsId = congregationId, tTerritoryCategoriesId = territoryCategoryId,
            tLocalitiesId = localityId, tLocalityDistrictsId = localityDistrictId,
            tMicrodistrictsId = microdistrictId,
            territoryId = territoryId,
            territoryNum = territoryNum, isBusinessTerritory = isBusiness, isInPerimeter = isInPerimeter,
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
        var result = tCongregationsId.hashCode()
        result = result * 31 + tTerritoryCategoriesId.hashCode()
        result = result * 31 + tLocalitiesId.hashCode()
        result = result * 31 + territoryNum.hashCode()
        tLocalityDistrictsId?.let { result = result * 31 + it.hashCode() }
        tMicrodistrictsId?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Entity №").append(territoryNum)
            .append(" [tCongregationsId = ").append(tCongregationsId)
            .append("; isActive = ").append(isActive)
            .append("; isBusiness = ").append(isBusinessTerritory)
            .append("; isGroupMinistry = ").append(isGroupMinistry)
            .append("; isInPerimeter = ").append(isInPerimeter)
            .append("; isProcessed = ").append(isProcessed)
        tLocalityDistrictsId?.let { str.append("; tLocalityDistrictsId = ").append(it) }
        tMicrodistrictsId?.let { str.append("; tMicrodistrictsId = ").append(it) }
        str.append("; tLocalitiesId = ").append(tLocalitiesId)
            .append("; tTerritoryCategoriesId = ").append(tTerritoryCategoriesId)
            .append("] territoryId = ").append(territoryId)
        return str.toString()
    }
}