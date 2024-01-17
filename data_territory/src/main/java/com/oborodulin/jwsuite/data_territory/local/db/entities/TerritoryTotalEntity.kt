package com.oborodulin.jwsuite.data_territory.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = TerritoryTotalEntity.TABLE_NAME,
    indices = [Index(value = ["ttlCongregationsId", "lastVisitDate"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("ttlCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class TerritoryTotalEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val territoryTotalId: UUID = UUID.randomUUID(),
    @Serializable(with = OffsetDateTimeSerializer::class)
    val lastVisitDate: OffsetDateTime? = null,
    val totalQty: Int,
    val totalIssued: Int,
    val totalProcessed: Int,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val ttlCongregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territory_totals"
    }

    override fun id() = this.territoryTotalId

    override fun key(): Int {
        var result = ttlCongregationsId.hashCode()
        result = result * 31 + lastVisitDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Total Entity totalQty = ").append(totalQty)
            .append("; totalIssued = ").append(totalIssued)
            .append("; totalProcessed = ").append(totalProcessed)
        lastVisitDate?.let {
            str.append(". Date of last visit ").append(DateTimeFormatter.ISO_LOCAL_DATE.format(it))
        }
        str.append(" [ttlCongregationsId = ").append(ttlCongregationsId)
            .append("] territoryTotalId = ").append(territoryTotalId)
        return str.toString()
    }
}