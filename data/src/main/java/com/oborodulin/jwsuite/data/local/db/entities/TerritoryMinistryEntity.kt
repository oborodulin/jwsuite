package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = TerritoryMinistryEntity.TABLE_NAME,
    indices = [Index(
        value = ["tmMembersId", "tmTerritoriesId", "tmHousesId", "tmRoomsId", "dateOfVisit"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = RoomEntity::class,
        parentColumns = arrayOf("roomId"),
        childColumns = arrayOf("tmRoomsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("tmHousesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryEntity::class,
        parentColumns = arrayOf("territoryId"),
        childColumns = arrayOf("tmTerritoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("tmMembersId"),
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
    @ColumnInfo(index = true) val tmRoomsId: UUID? = null,
    @ColumnInfo(index = true) val tmHousesId: UUID? = null,
    @ColumnInfo(index = true) val tmTerritoriesId: UUID,
    @ColumnInfo(index = true) val tmMembersId: UUID
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
            tmMembersId = memberId, territoryMinistryId = territoryMinistryId,
            tmTerritoriesId = territoryId, tmHousesId = houseId, tmRoomsId = roomId,
            dateOfVisit = dateOfVisit, personsName = personsName, gender = gender, age = age,
            subject = subject, nextQuestion = nextQuestion, ministryDesc = ministryDesc
        )
    }

    override fun id() = this.territoryMinistryId

    override fun key(): Int {
        var result = tmMembersId.hashCode()
        result = result * 31 + tmTerritoriesId.hashCode()
        result = result * 31 + dateOfVisit.hashCode()
         result = result * 31 + tmHousesId.hashCode()
         result = result * 31 + tmRoomsId.hashCode()
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