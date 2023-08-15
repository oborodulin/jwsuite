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
        value = ["ctCongregationsId", "ctTerritoriesId", "startUsingDate"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("ctCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("ctTerritoriesId"),
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
    @ColumnInfo(index = true) val ctTerritoriesId: UUID,
    @ColumnInfo(index = true) val ctCongregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "congregations_territories"

        fun defaultCongregationTerritory(
            congregationId: UUID, territoryId: UUID,
            startUsingDate: OffsetDateTime = OffsetDateTime.now(),
            endUsingDate: OffsetDateTime? = null,
        ) = CongregationTerritoryCrossRefEntity(
            ctCongregationsId = congregationId, ctTerritoriesId = territoryId,
            startUsingDate = startUsingDate, endUsingDate = endUsingDate
        )
    }

    override fun id() = this.congregationTerritoryId

    override fun key(): Int {
        var result = ctCongregationsId.hashCode()
        result = result * 31 + ctTerritoriesId.hashCode()
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
        str.append(" [ctCongregationsId = ").append(ctCongregationsId)
            .append("; ctTerritoriesId = ").append(ctTerritoriesId)
            .append("] congregationTerritoryId = ").append(congregationTerritoryId)
        return str.toString()
    }
}