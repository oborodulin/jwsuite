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
    tableName = MemberMinistryEntity.TABLE_NAME,
    indices = [Index(value = ["membersId", "ministryDate"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("membersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class MemberMinistryEntity(
    @PrimaryKey val memberMinistryId: UUID = UUID.randomUUID(),
    val ministryDate: OffsetDateTime = OffsetDateTime.now(),
    val hoursQty: Int? = null,
    val returnVisitsQty: Int? = null,
    val mediaQty: Int? = null,  // video and audio
    val publicationsQty: Int? = null,
    val studiesQty: Int? = null,
    val ministryDesc: String? = null,
    @ColumnInfo(index = true) val membersId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "member_ministries"

        fun defaultMemberMinistry(
            memberMinistryId: UUID = UUID.randomUUID(), memberId: UUID = UUID.randomUUID(),
            ministryDate: OffsetDateTime = OffsetDateTime.now(),
            hoursQty: Int? = null, returnVisitsQty: Int? = null, mediaQty: Int? = null,
            publicationsQty: Int? = null, studiesQty: Int? = null, ministryDesc: String? = null
        ) = MemberMinistryEntity(
            membersId = memberId, memberMinistryId = memberMinistryId,
            ministryDate = ministryDate, hoursQty = hoursQty, returnVisitsQty = returnVisitsQty,
            mediaQty = mediaQty, publicationsQty = publicationsQty, studiesQty = studiesQty,
            ministryDesc = ministryDesc
        )

    }

    override fun id() = this.memberMinistryId

    override fun key(): Int {
        var result = membersId.hashCode()
        result = result * 31 + ministryDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Member Ministry Entity by ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(ministryDate)).append(" [")
        hoursQty?.let { str.append("; hoursQty = ").append(it) }
        returnVisitsQty?.let { str.append("; returnVisitsQty = ").append(it) }
        mediaQty?.let { str.append("; mediaQty = ").append(it) }
        publicationsQty?.let { str.append("; publicationsQty = ").append(it) }
        studiesQty?.let { str.append("; studiesQty = ").append(it) }
        ministryDesc?.let { str.append("; ministryDesc = ").append(it) }
        str.append("] memberMinistryId = ").append(memberMinistryId)
        return str.toString()
    }
}