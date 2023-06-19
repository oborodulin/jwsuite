package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import java.util.UUID

@Entity(
    tableName = TerritoryCategoryEntity.TABLE_NAME,
    indices = [Index(value = ["territoryCategoryCode"], unique = true)],
)
data class TerritoryCategoryEntity(
    @PrimaryKey val territoryCategoryId: UUID = UUID.randomUUID(),
    val territoryCategoryCode: String,
    val territoryCategoryMark: String,
    val territoryCategoryName: String
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territory_categories"

        fun defaultTerritoryCategory(
            territoryCategoryId: UUID = UUID.randomUUID(), territoryCategoryCode: String,
            territoryCategoryMark: String, territoryCategoryName: String
        ) = TerritoryCategoryEntity(
            territoryCategoryId = territoryCategoryId,
            territoryCategoryCode = territoryCategoryCode,
            territoryCategoryMark = territoryCategoryMark,
            territoryCategoryName = territoryCategoryName
        )

        fun houseTerritoryCategory(ctx: Context) = defaultTerritoryCategory(
            territoryCategoryCode = ctx.resources.getString(R.string.def_house_territory_category_code),
            territoryCategoryMark = ctx.resources.getString(R.string.def_house_territory_category_mark),
            territoryCategoryName = ctx.resources.getString(R.string.def_house_territory_category_name)
        )

        fun floorTerritoryCategory(ctx: Context) = defaultTerritoryCategory(
            territoryCategoryCode = ctx.resources.getString(R.string.def_floor_territory_category_code),
            territoryCategoryMark = ctx.resources.getString(R.string.def_floor_territory_category_mark),
            territoryCategoryName = ctx.resources.getString(R.string.def_floor_territory_category_name)
        )

        fun roomTerritoryCategory(ctx: Context) = defaultTerritoryCategory(
            territoryCategoryCode = ctx.resources.getString(R.string.def_room_territory_category_code),
            territoryCategoryMark = ctx.resources.getString(R.string.def_room_territory_category_mark),
            territoryCategoryName = ctx.resources.getString(R.string.def_room_territory_category_name)
        )
    }

    override fun id() = this.territoryCategoryId

    override fun key() = this.territoryCategoryCode.hashCode()

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Category Entity ").append(territoryCategoryCode)
            .append(" ").append(territoryCategoryMark)
            .append(": '").append(territoryCategoryName)
            .append("' territoryCategoryId = ").append(territoryCategoryId)
        return str.toString()
    }
}