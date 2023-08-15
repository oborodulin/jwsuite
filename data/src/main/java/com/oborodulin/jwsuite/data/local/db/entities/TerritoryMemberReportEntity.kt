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
        value = ["tmrTerritoryMembersId", "tmrTerritoryStreetsId", "tmrHousesId", "tmrRoomsId"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = TerritoryMemberCrossRefEntity::class,
        parentColumns = arrayOf("territoryMemberId"),
        childColumns = arrayOf("tmrTerritoryMembersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = RoomEntity::class,
        parentColumns = arrayOf("roomId"),
        childColumns = arrayOf("tmrRoomsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = HouseEntity::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("tmrHousesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryStreetEntity::class,
        parentColumns = arrayOf("territoryStreetId"),
        childColumns = arrayOf("tmrTerritoryStreetsId"),
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
    @ColumnInfo(index = true) val tmrRoomsId: UUID? = null,
    @ColumnInfo(index = true) val tmrHousesId: UUID? = null,
    @ColumnInfo(index = true) val tmrTerritoryStreetsId: UUID? = null,
    @ColumnInfo(index = true) val tmrTerritoryMembersId: UUID
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
            tmrTerritoryMembersId = territoryMemberId,
            territoryMemberReportId = territoryMemberReportId,
            tmrTerritoryStreetsId = territoryStreetId, tmrHousesId = houseId, tmrRoomsId = roomId,
            territoryMemberMark = territoryMemberMark, languageCode = languageCode,
            gender = gender, age = age,
            isProcessed = isProcessed, territoryReportDesc = territoryReportDesc
        )

    }

    override fun id() = this.territoryMemberReportId

    override fun key(): Int {
        var result = tmrTerritoryMembersId.hashCode()
        result = result * 31 + tmrTerritoryStreetsId.hashCode()
        result = result * 31 + tmrHousesId.hashCode()
        result = result * 31 + tmrRoomsId.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Member Report Entity ")
            .append(" [territoryMemberMark = ").append(territoryMemberMark)
            .append("; isProcessed = ").append(isProcessed)
        tmrTerritoryStreetsId?.let { str.append("; tmrTerritoryStreetsId = ").append(it) }
        tmrHousesId?.let { str.append("; tmrHousesId = ").append(it) }
        tmrRoomsId?.let { str.append("; tmrRoomsId = ").append(it) }
        territoryReportDesc?.let { str.append("; territoryReportDesc = ").append(it) }
        str.append("] territoryMemberReportId = ").append(territoryMemberReportId)
        return str.toString()
    }
}