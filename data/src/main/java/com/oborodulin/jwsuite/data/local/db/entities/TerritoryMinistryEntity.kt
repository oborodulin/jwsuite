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
    tableName = TerritoryMinistryEntity.TABLE_NAME,
    indices = [Index(
        value = ["membersId", "territoriesId", "housesId", "roomsId", "dateOfVisit"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = RoomEntity::class,
        parentColumns = arrayOf("roomId"),
        childColumns = arrayOf("roomsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("housesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
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
data class TerritoryMinistryEntity(
    @PrimaryKey val territoryMinistryId: UUID = UUID.randomUUID(),
    val dateOfVisit: OffsetDateTime = OffsetDateTime.now(),
    val personsName: String? = null,
    val gender: Boolean? = null,
    val age: Int? = null,
    val subject: String? = null,
    val nextQuestion: String? = null,
    val ministryDesc: String? = null,
    @ColumnInfo(index = true) val roomsId: UUID? = null,
    @ColumnInfo(index = true) val housesId: UUID? = null,
    @ColumnInfo(index = true) val territoriesId: UUID,
    @ColumnInfo(index = true) val membersId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territory_ministries"

        fun defaultTerritoryMinistry(
            territoryMinistryId: UUID = UUID.randomUUID(), memberId: UUID = UUID.randomUUID(),
            territoryId: UUID = UUID.randomUUID(),
            houseId: UUID? = null, roomId: UUID? = null,
            dateOfVisit: OffsetDateTime = OffsetDateTime.now(), personsName: String? = null,
            gender: Boolean? = null, age: Int? = null,
            subject: String? = null, nextQuestion: String? = null, ministryDesc: String? = null
        ) = TerritoryMinistryEntity(
            membersId = memberId, territoryMinistryId = territoryMinistryId,
            territoriesId = territoryId, housesId = houseId, roomsId = roomId,
            dateOfVisit = dateOfVisit, personsName = personsName, gender = gender, age = age,
            subject = subject, nextQuestion = nextQuestion, ministryDesc = ministryDesc
        )
    }

    override fun id() = this.territoryMinistryId

    override fun key(): Int {
        var result = membersId.hashCode()
        result = result * 31 + territoriesId.hashCode()
        result = result * 31 + dateOfVisit.hashCode()
        housesId?.let { result = result * 31 + it.hashCode() }
        roomsId?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Ministry Entity ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(dateOfVisit)).append(": [")
        personsName?.let { str.append("; personsName = ").append(it) }
        gender?.let { str.append("; gender = ").append(it) }
        age?.let { str.append("; age = ").append(it) }
        subject?.let { str.append("; subject = ").append(it) }
        nextQuestion?.let { str.append("; nextQuestion = ").append(it) }
        ministryDesc?.let { str.append("; ministryDesc = ").append(it) }
        str.append("] territoryMinistryId = ").append(territoryMinistryId)
        return str.toString()
    }
}