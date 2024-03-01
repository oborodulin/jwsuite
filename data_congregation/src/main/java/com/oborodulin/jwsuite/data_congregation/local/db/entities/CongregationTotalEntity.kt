package com.oborodulin.jwsuite.data_congregation.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = CongregationTotalEntity.TABLE_NAME,
    indices = [Index(value = ["ctlCongregationsId", "lastVisitDate"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("ctlCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class CongregationTotalEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val congregationTotalId: UUID = UUID.randomUUID(),
    @Serializable(with = OffsetDateTimeSerializer::class)
    val lastVisitDate: OffsetDateTime? = null,
    val totalGroups: Int = 0,
    val totalMembers: Int = 0,
    val totalActiveMembers: Int = 0,
    val totalFulltimeMembers: Int = 0,
    val totalTerritories: Int = 0,
    val totalTerritoryIssued: Int = 0,
    val totalTerritoryProcessed: Int = 0,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val ctlCongregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "congregation_totals"
    }

    override fun id() = this.congregationTotalId

    override fun key(): Int {
        var result = ctlCongregationsId.hashCode()
        result = result * 31 + lastVisitDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Congregation Total Entity totalGroups = ").append(totalGroups)
            .append("; totalMembers = ").append(totalMembers)
            .append("; totalActiveMembers = ").append(totalFulltimeMembers)
            .append("; totalFulltimeMembers = ").append(totalFulltimeMembers)
            .append("; totalTerritories = ").append(totalTerritories)
            .append("; totalTerritoryIssued = ").append(totalTerritoryIssued)
            .append("; totalTerritoryProcessed = ").append(totalTerritoryProcessed)
        lastVisitDate?.let {
            str.append(". Date of last visit ").append(DateTimeFormatter.ISO_LOCAL_DATE.format(it))
        }
        str.append(" [ctlCongregationsId = ").append(ctlCongregationsId)
            .append("] congregationTotalId = ").append(congregationTotalId)
        return str.toString()
    }
}