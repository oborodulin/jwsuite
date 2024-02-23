package com.oborodulin.jwsuite.data_congregation.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_congregation.R
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = MemberEntity.TABLE_NAME,
    indices = [Index(
        //value = ["mGroupsId", "memberNum", "pseudonym", "memberName", "surname", "patronymic", "dateOfBirth"],
        value = ["pseudonym"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GroupEntity::class,
        parentColumns = arrayOf("groupId"),
        childColumns = arrayOf("mGroupsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class MemberEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val memberId: UUID = UUID.randomUUID(),
    val memberNum: String? = null,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String,
    val phoneNumber: String? = null,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val dateOfBirth: OffsetDateTime? = null,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val dateOfBaptism: OffsetDateTime? = null,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val loginExpiredDate: OffsetDateTime? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mGroupsId: UUID? = null
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "members"
        const val PX_CONGREGATION = "mc_"
        const val PX_REGION = PX_CONGREGATION + CongregationEntity.PX_REGION
        const val PX_REGION_DISTRICT = PX_CONGREGATION + CongregationEntity.PX_REGION_DISTRICT
        const val PX_LOCALITY = PX_CONGREGATION + CongregationEntity.PX_LOCALITY

        fun defaultMember(
            memberId: UUID = UUID.randomUUID(), groupId: UUID? = null,
            memberNum: String? = null, memberName: String? = null, surname: String? = null,
            patronymic: String? = null, pseudonym: String, phoneNumber: String? = null,
            dateOfBirth: OffsetDateTime? = null, dateOfBaptism: OffsetDateTime? = null
        ) = MemberEntity(
            memberId = memberId, mGroupsId = groupId,
            memberNum = memberNum, memberName = memberName, surname = surname,
            patronymic = patronymic, pseudonym = pseudonym,
            phoneNumber = phoneNumber, dateOfBirth = dateOfBirth, dateOfBaptism = dateOfBaptism
        )

        fun adminMember(ctx: Context) = defaultMember(
            memberName = ctx.resources.getString(R.string.def_admin_member_name),
            pseudonym = ctx.resources.getString(R.string.def_admin_member_pseudonym)
        )

        fun ivanovMember11(ctx: Context, groupId: UUID? = null) = defaultMember(
            groupId = groupId,
            memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
            memberName = ctx.resources.getString(R.string.def_ivanov_member_name),
            surname = ctx.resources.getString(R.string.def_ivanov_member_surname),
            patronymic = ctx.resources.getString(R.string.def_ivanov_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_ivanov_member_pseudonym)
        )

        fun petrovMember12(ctx: Context, groupId: UUID? = null) = defaultMember(
            groupId = groupId,
            memberNum = ctx.resources.getString(R.string.def_petrov_member_num),
            memberName = ctx.resources.getString(R.string.def_petrov_member_name),
            surname = ctx.resources.getString(R.string.def_petrov_member_surname),
            patronymic = ctx.resources.getString(R.string.def_petrov_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_petrov_member_pseudonym)
        )

        fun sidorovMember21(ctx: Context, groupId: UUID? = null) = defaultMember(
            groupId = groupId,
            memberNum = ctx.resources.getString(R.string.def_sidorov_member_num),
            memberName = ctx.resources.getString(R.string.def_sidorov_member_name),
            surname = ctx.resources.getString(R.string.def_sidorov_member_surname),
            patronymic = ctx.resources.getString(R.string.def_sidorov_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_sidorov_member_pseudonym)
        )

        fun tarasovaMember11(ctx: Context, groupId: UUID? = null) = defaultMember(
            groupId = groupId,
            memberNum = ctx.resources.getString(R.string.def_tarasova_member_num),
            memberName = ctx.resources.getString(R.string.def_tarasova_member_name),
            surname = ctx.resources.getString(R.string.def_tarasova_member_surname),
            patronymic = ctx.resources.getString(R.string.def_tarasova_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_tarasova_member_pseudonym)
        )

        fun shevchukMember12(ctx: Context, groupId: UUID? = null) = defaultMember(
            groupId = groupId,
            memberNum = ctx.resources.getString(R.string.def_shevchuk_member_num),
            memberName = ctx.resources.getString(R.string.def_shevchuk_member_name),
            surname = ctx.resources.getString(R.string.def_shevchuk_member_surname),
            patronymic = ctx.resources.getString(R.string.def_shevchuk_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_shevchuk_member_pseudonym)
        )

        fun matveychukMember21(ctx: Context, groupId: UUID? = null) = defaultMember(
            groupId = groupId,
            memberNum = ctx.resources.getString(R.string.def_matveychuk_member_num),
            memberName = ctx.resources.getString(R.string.def_matveychuk_member_name),
            surname = ctx.resources.getString(R.string.def_matveychuk_member_surname),
            patronymic = ctx.resources.getString(R.string.def_matveychuk_member_patronymic),
            pseudonym = ctx.resources.getString(R.string.def_matveychuk_member_pseudonym)
        )
    }

    override fun id() = this.memberId

    override fun key(): Int {
        var result = mGroupsId.hashCode()
        result = result * 31 + memberNum.hashCode()
        result = result * 31 + pseudonym.hashCode()
        result = result * 31 + memberName.hashCode()
        result = result * 31 + surname.hashCode()
        result = result * 31 + patronymic.hashCode()
        result = result * 31 + dateOfBirth.hashCode()
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
        str.append(" [mGroupsId = ").append(mGroupsId)
            .append("] memberId = ").append(memberId)
        return str.toString()
    }
}