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
        value = ["congregationsId", "territoryCategoriesId", "territoryNum"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
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
    @ColumnInfo(index = true) val territoryCategoriesId: UUID,
    @ColumnInfo(index = true) val congregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territories"

        fun defaultTerritory(
            territoryId: UUID = UUID.randomUUID(), territoryCategoryId: UUID = UUID.randomUUID(),
            congregationId: UUID = UUID.randomUUID(),
            territoryNum: Int, isBusiness: Boolean = false, isInPerimeter: Boolean = false,
            isProcessed: Boolean = false, isActive: Boolean = true, territoryDesc: String? = null
        ) = TerritoryEntity(
            congregationsId = congregationId, territoryCategoriesId = territoryCategoryId,
            territoryId = territoryId,
            territoryNum = territoryNum, isBusiness = isBusiness, isInPerimeter = isInPerimeter,
            isProcessed = isProcessed, isActive = isActive, territoryDesc = territoryDesc
        )

        fun businessTerritory(
            congregationId: UUID = UUID.randomUUID(), territoryCategoryId: UUID = UUID.randomUUID(),
            territoryNum: Int, territoryDesc: String? = null
        ) = defaultTerritory(
            congregationId = congregationId, territoryCategoryId = territoryCategoryId,
            territoryNum = territoryNum, isBusiness = true,
            territoryDesc = territoryDesc
        )
    }

    override fun id() = this.territoryId

    override fun key(): Int {
        var result = congregationsId.hashCode()
        result = result * 31 + territoryNum.hashCode()
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
            .append("; territoryCategoriesId = ").append(territoryCategoriesId)
            .append("] territoryId = ").append(territoryId)
        return str.toString()
    }
}