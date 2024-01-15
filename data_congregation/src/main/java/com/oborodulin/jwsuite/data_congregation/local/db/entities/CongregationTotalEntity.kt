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
    val totalMembers: Int,
    val totalFulltimeMembers: Int,
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
        str.append("Congregation Total Entity totalMembers = ").append(totalMembers)
            .append("; totalFulltimeMembers = ").append(totalMembers)
        lastVisitDate?.let {
            str.append(". Date of last visit ").append(DateTimeFormatter.ISO_LOCAL_DATE.format(it))
        }
        str.append(" [ctlCongregationsId = ").append(ctlCongregationsId)
            .append("] congregationTotalId = ").append(congregationTotalId)
        return str.toString()
    }
}