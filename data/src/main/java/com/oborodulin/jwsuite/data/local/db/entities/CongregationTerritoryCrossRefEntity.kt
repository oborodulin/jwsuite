package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = CongregationTerritoryCrossRefEntity.TABLE_NAME,
    indices = [Index(
        value = ["congregationsId", "territoriesId", "startUsingDate"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("congregationsId"),
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
data class CongregationTerritoryCrossRefEntity(
    @PrimaryKey val congregationTerritoryId: UUID = UUID.randomUUID(),
    val startUsingDate: OffsetDateTime = OffsetDateTime.now(),
    val endUsingDate: OffsetDateTime? = null,
    // warning: servicesId column references a foreign key but it is not part of an index.
    // This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers this column.
    @ColumnInfo(index = true) val territoriesId: UUID,
    @ColumnInfo(index = true) val congregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "congregations_territories"

        fun defaultCongregationTerritory(
            congregationId: UUID, territoryId: UUID,
            startUsingDate: OffsetDateTime = OffsetDateTime.now(),
            endUsingDate: OffsetDateTime? = null,
        ) = CongregationTerritoryCrossRefEntity(
            congregationsId = congregationId, territoriesId = territoryId,
            startUsingDate = startUsingDate, endUsingDate = endUsingDate
        )
    }

    override fun id() = this.congregationTerritoryId

    override fun key(): Int {
        var result = congregationsId.hashCode()
        result = result * 31 + territoriesId.hashCode()
        result = result * 31 + startUsingDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Congregation Territory Entity").append(" for period ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(startUsingDate))
            .append(" - ").append(
                if (endUsingDate != null) DateTimeFormatter.ISO_LOCAL_DATE.format(endUsingDate) else "..."
            )
        str.append(" [congregationsId = ").append(congregationsId)
            .append("; territoriesId = ").append(territoriesId)
            .append("] congregationTerritoryId = ").append(congregationTerritoryId)
        return str.toString()
    }
}