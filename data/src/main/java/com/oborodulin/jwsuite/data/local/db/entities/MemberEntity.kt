package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.util.MemberType
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = MemberEntity.TABLE_NAME,
    indices = [Index(
        value = ["groupsId", "memberNum", "memberName", "surname", "patronymic", "pseudonym", "memberType", "dateOfBirth"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GroupEntity::class,
        parentColumns = arrayOf("groupId"),
        childColumns = arrayOf("groupsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class MemberEntity(
    @PrimaryKey val memberId: UUID = UUID.randomUUID(),
    val memberNum: String,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String,
    val phoneNumber: String? = null,
    val memberType: MemberType = MemberType.PREACHER,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val inactiveDate: OffsetDateTime? = null,
    @ColumnInfo(index = true) val groupsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "members"

        fun defaultMember(
            memberId: UUID = UUID.randomUUID(), groupId: UUID = UUID.randomUUID(),
            memberNum: String, memberName: String? = null, surname: String? = null,
            patronymic: String? = null, pseudonym: String, phoneNumber: String? = null,
            memberType: MemberType = MemberType.PREACHER,
            dateOfBirth: OffsetDateTime? = null, dateOfBaptism: OffsetDateTime? = null,
            inactiveDate: OffsetDateTime? = null
        ) = MemberEntity(
            memberId = memberId, groupsId = groupId,
            memberNum = memberNum, memberName = memberName, surname = surname,
            patronymic = patronymic, pseudonym = pseudonym,
            phoneNumber = phoneNumber, memberType = memberType,
            dateOfBirth = dateOfBirth, dateOfBaptism = dateOfBaptism, inactiveDate = inactiveDate
        )

        fun ivanovMember11(ctx: Context, groupId: UUID) = MemberEntity(
            groupsId = groupId,
            memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
            memberName = ctx.resources.getString(R.string.def_ivanov_member_name),
            surname = ctx.resources.getString(R.string.def_ivanov_member_surname),
            patronymic = ctx.resources.getString(R.string.def_ivanov_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_ivanov_member_pseudonym)
        )

        fun petrovMember12(ctx: Context, groupId: UUID) = MemberEntity(
            groupsId = groupId,
            memberNum = ctx.resources.getString(R.string.def_petrov_member_num),
            memberName = ctx.resources.getString(R.string.def_petrov_member_name),
            surname = ctx.resources.getString(R.string.def_petrov_member_surname),
            patronymic = ctx.resources.getString(R.string.def_petrov_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_petrov_member_pseudonym)
        )

        fun sidorovMember21(ctx: Context, groupId: UUID) = MemberEntity(
            groupsId = groupId,
            memberNum = ctx.resources.getString(R.string.def_sidorov_member_num),
            memberName = ctx.resources.getString(R.string.def_sidorov_member_name),
            surname = ctx.resources.getString(R.string.def_sidorov_member_surname),
            patronymic = ctx.resources.getString(R.string.def_sidorov_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_sidorov_member_pseudonym)
        )

        fun tarasovaMember11(ctx: Context, groupId: UUID) = MemberEntity(
            groupsId = groupId,
            memberNum = ctx.resources.getString(R.string.def_tarasova_member_num),
            memberName = ctx.resources.getString(R.string.def_tarasova_member_name),
            surname = ctx.resources.getString(R.string.def_tarasova_member_surname),
            patronymic = ctx.resources.getString(R.string.def_tarasova_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_tarasova_member_pseudonym)
        )

        fun shevchukMember12(ctx: Context, groupId: UUID) = MemberEntity(
            groupsId = groupId,
            memberNum = ctx.resources.getString(R.string.def_shevchuk_member_num),
            memberName = ctx.resources.getString(R.string.def_shevchuk_member_name),
            surname = ctx.resources.getString(R.string.def_shevchuk_member_surname),
            patronymic = ctx.resources.getString(R.string.def_shevchuk_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_shevchuk_member_pseudonym)
        )

        fun matveychukMember21(ctx: Context, groupId: UUID) = MemberEntity(
            groupsId = groupId,
            memberNum = ctx.resources.getString(R.string.def_matveychuk_member_num),
            memberName = ctx.resources.getString(R.string.def_matveychuk_member_name),
            surname = ctx.resources.getString(R.string.def_matveychuk_member_surname),
            patronymic = ctx.resources.getString(R.string.def_matveychuk_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_matveychuk_member_pseudonym)
        )
    }

    override fun id() = this.memberId

    override fun key(): Int {
        var result = groupsId.hashCode()
        result = result * 31 + memberNum.hashCode()
        result = result * 31 + pseudonym.hashCode()
        result = result * 31 + memberType.hashCode()
        memberName?.let { result = result * 31 + it.hashCode() }
        surname?.let { result = result * 31 + it.hashCode() }
        patronymic?.let { result = result * 31 + it.hashCode() }
        dateOfBirth?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Member Entity №").append(memberNum).append(" '").append(pseudonym).append("' ")
        memberName?.let { str.append(it).append(" ") }
        surname?.let { str.append(it).append(" ") }
        patronymic?.let { str.append(it).append(" ") }
        dateOfBirth?.let {
            str.append(". Date of Birth ").append(DateTimeFormatter.ISO_LOCAL_DATE.format(it))
        }
        dateOfBaptism?.let {
            str.append(". Date of Baptism ").append(DateTimeFormatter.ISO_LOCAL_DATE.format(it))
        }
        str.append(" [groupsId = ").append(groupsId)
            .append("] memberId = ").append(memberId)
        return str.toString()
    }
}