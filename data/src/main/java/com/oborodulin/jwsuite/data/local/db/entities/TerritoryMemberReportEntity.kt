package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.domain.util.TerritoryMemberMark
import java.util.UUID

@Entity(
    tableName = TerritoryMemberReportEntity.TABLE_NAME,
    indices = [Index(
        value = ["territoryMembersId", "territoryStreetsId", "housesId", "roomsId"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = TerritoryMemberCrossRefEntity::class,
        parentColumns = arrayOf("territoryMemberId"),
        childColumns = arrayOf("territoryMembersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
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
        entity = TerritoryStreetEntity::class,
        parentColumns = arrayOf("territoryStreetId"),
        childColumns = arrayOf("territoryStreetsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class TerritoryMemberReportEntity(
    @PrimaryKey val territoryMemberReportId: UUID = UUID.randomUUID(),
    val territoryMemberMark: TerritoryMemberMark? = null,
    val languageCode: String? = null,
    val gender: Boolean? = null,
    val age: Int? = null,
    val isProcessed: Boolean = false,
    val territoryReportDesc: String? = null,
    @ColumnInfo(index = true) val roomsId: UUID? = null,
    @ColumnInfo(index = true) val housesId: UUID? = null,
    @ColumnInfo(index = true) val territoryStreetsId: UUID? = null,
    @ColumnInfo(index = true) val territoryMembersId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territory_member_reports"

        fun defaultTerritoryMemberReport(
            territoryMemberReportId: UUID = UUID.randomUUID(),
            territoryMemberId: UUID = UUID.randomUUID(),
            territoryStreetId: UUID? = null, houseId: UUID? = null, roomId: UUID? = null,
            territoryMemberMark: TerritoryMemberMark? = null,
            languageCode: String? = null,
            gender: Boolean? = null, age: Int? = null,
            isProcessed: Boolean = false,
            territoryReportDesc: String? = null
        ) = TerritoryMemberReportEntity(
            territoryMembersId = territoryMemberId,
            territoryMemberReportId = territoryMemberReportId,
            territoryStreetsId = territoryStreetId, housesId = houseId, roomsId = roomId,
            territoryMemberMark = territoryMemberMark, languageCode = languageCode,
            gender = gender, age = age,
            isProcessed = isProcessed, territoryReportDesc = territoryReportDesc
        )

    }

    override fun id() = this.territoryMemberReportId

    override fun key(): Int {
        var result = territoryMembersId.hashCode()
        territoryStreetsId?.let { result = result * 31 + it.hashCode() }
        housesId?.let { result = result * 31 + it.hashCode() }
        roomsId?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Member Report Entity ")
            .append(" [territoryMemberMark = ").append(territoryMemberMark)
            .append("; isProcessed = ").append(isProcessed)
        territoryStreetsId?.let { str.append("; territoryStreetsId = ").append(it) }
        housesId?.let { str.append("; housesId = ").append(it) }
        roomsId?.let { str.append("; roomsId = ").append(it) }
        territoryReportDesc?.let { str.append("; territoryReportDesc = ").append(it) }
        str.append("] territoryMemberReportId = ").append(territoryMemberReportId)
        return str.toString()
    }
}