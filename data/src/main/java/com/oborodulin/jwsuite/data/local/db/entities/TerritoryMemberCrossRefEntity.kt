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
    tableName = TerritoryMemberCrossRefEntity.TABLE_NAME,
    indices = [Index(
        value = ["territoriesId", "membersId", "receivingDate"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("territoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("membersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class TerritoryMemberCrossRefEntity(
    @PrimaryKey val territoryMemberId: UUID = UUID.randomUUID(),
    val receivingDate: OffsetDateTime = OffsetDateTime.now(),
    val deliveryDate: OffsetDateTime? = null,
    // warning: servicesId column references a foreign key but it is not part of an index.
    // This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers this column.
    @ColumnInfo(index = true) val territoriesId: UUID,
    @ColumnInfo(index = true) val membersId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territories_members"

        fun defaultTerritoryMember(
            territoryId: UUID, memberId: UUID,
            receivingDate: OffsetDateTime = OffsetDateTime.now(),
            deliveryDate: OffsetDateTime? = null
        ) = TerritoryMemberCrossRefEntity(
            territoriesId = territoryId, membersId = memberId,
            receivingDate = receivingDate, deliveryDate = deliveryDate
        )
    }

    override fun id() = this.territoryMemberId

    override fun key(): Int {
        var result = territoriesId.hashCode()
        result = result * 31 + membersId.hashCode()
        result = result * 31 + receivingDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Member Entity").append(" for period ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(receivingDate))
            .append(" - ").append(
                if (deliveryDate != null) DateTimeFormatter.ISO_LOCAL_DATE.format(deliveryDate) else "..."
            )
        str.append(" [territoriesId = ").append(territoriesId)
            .append("; membersId = ").append(membersId)
            .append("] territoryMemberId = ").append(territoryMemberId)
        return str.toString()
    }
}