package com.oborodulin.jwsuite.data_territory.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = TerritoryMemberCrossRefEntity.TABLE_NAME,
    indices = [Index(
        value = ["tmcTerritoriesId", "tmcMembersId", "receivingDate"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("tmcTerritoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("tmcMembersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class TerritoryMemberCrossRefEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val territoryMemberId: UUID = UUID.randomUUID(),
    @Serializable(with = OffsetDateTimeSerializer::class)
    val receivingDate: OffsetDateTime = OffsetDateTime.now(),
    @Serializable(with = OffsetDateTimeSerializer::class)
    val deliveryDate: OffsetDateTime? = null,
    // warning: servicesId column references a foreign key but it is not part of an index.
    // This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers this column.
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val tmcTerritoriesId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val tmcMembersId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territories_members"

        fun defaultTerritoryMember(
            territoryId: UUID, memberId: UUID,
            receivingDate: OffsetDateTime = OffsetDateTime.now(),
            deliveryDate: OffsetDateTime? = null
        ) = TerritoryMemberCrossRefEntity(
            tmcTerritoriesId = territoryId, tmcMembersId = memberId,
            receivingDate = receivingDate, deliveryDate = deliveryDate
        )
    }

    override fun id() = this.territoryMemberId

    override fun key(): Int {
        var result = tmcTerritoriesId.hashCode()
        result = result * 31 + tmcMembersId.hashCode()
        result = result * 31 + receivingDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Member Entity").append(" for period ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(receivingDate))
            .append(" - ").append(
                deliveryDate?.let { DateTimeFormatter.ISO_LOCAL_DATE.format(it) } ?: "..."
            )
        str.append(" [tmcTerritoriesId = ").append(tmcTerritoriesId)
            .append("; tmcMembersId = ").append(tmcMembersId)
            .append("] territoryMemberId = ").append(territoryMemberId)
        return str.toString()
    }
}