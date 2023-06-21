package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import java.util.UUID

@Entity(
    tableName = GroupEntity.TABLE_NAME,
    indices = [Index(value = ["gCongregationsId", "groupNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("gCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class GroupEntity(
    @PrimaryKey val groupId: UUID = UUID.randomUUID(),
    val groupNum: Int,
    @ColumnInfo(index = true) val gCongregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "groups"

        fun defaultGroup(
            groupId: UUID = UUID.randomUUID(), congregationId: UUID = UUID.randomUUID(),
            groupNum: Int
        ) = GroupEntity(
            gCongregationsId = congregationId, groupId = groupId, groupNum = groupNum
        )

        fun group1(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group1),
            congregationId = congregationId
        )

        fun group2(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group2),
            congregationId = congregationId
        )

        fun group3(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group3),
            congregationId = congregationId
        )

        fun group4(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group4),
            congregationId = congregationId
        )

        fun group5(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group5),
            congregationId = congregationId
        )

        fun group6(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group6),
            congregationId = congregationId
        )

        fun group7(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group7),
            congregationId = congregationId
        )

        fun group8(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group8),
            congregationId = congregationId
        )

        fun group9(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group9),
            congregationId = congregationId
        )

        fun group10(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group10),
            congregationId = congregationId
        )

        fun group11(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group11),
            congregationId = congregationId
        )

        fun group12(ctx: Context, congregationId: UUID) = defaultGroup(
            groupNum = ctx.resources.getInteger(R.integer.def_group12),
            congregationId = congregationId
        )

    }

    override fun id() = this.groupId

    override fun key(): Int {
        var result = gCongregationsId.hashCode()
        result = result * 31 + groupNum.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Group Entity №").append(groupNum)
            .append(" [congregationsId = ").append(gCongregationsId)
            .append("] groupId = ").append(groupId)
        return str.toString()
    }
}