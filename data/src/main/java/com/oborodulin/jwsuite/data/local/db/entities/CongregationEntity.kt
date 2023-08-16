package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import java.util.UUID

@Entity(
    tableName = CongregationEntity.TABLE_NAME,
    indices = [Index(value = ["congregationNum"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("cLocalitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class CongregationEntity(
    @PrimaryKey val congregationId: UUID = UUID.randomUUID(),
    val congregationNum: String,
    val congregationName: String,
    val territoryMark: String,
    val isFavorite: Boolean = false,
    @ColumnInfo(index = true) val cLocalitiesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "congregations"

        fun defaultCongregation(
            congregationId: UUID = UUID.randomUUID(), localityId: UUID = UUID.randomUUID(),
            congregationNum: String, congregationName: String, territoryMark: String,
            isFavorite: Boolean = false
        ) = CongregationEntity(
            congregationId = congregationId, cLocalitiesId = localityId,
            congregationNum = congregationNum, congregationName = congregationName,
            territoryMark = territoryMark, isFavorite = isFavorite
        )

        fun favoriteCongregation(ctx: Context, localityId: UUID) = defaultCongregation(
            localityId = localityId,
            congregationNum = ctx.resources.getString(R.string.def_congregation1_num),
            congregationName = ctx.resources.getString(R.string.def_congregation1_name),
            territoryMark = ctx.resources.getString(R.string.def_congregation1_card_mark),
            isFavorite = true
        )

        fun secondCongregation(ctx: Context, localityId: UUID) = defaultCongregation(
            localityId = localityId,
            congregationNum = ctx.resources.getString(R.string.def_congregation2_num),
            congregationName = ctx.resources.getString(R.string.def_congregation2_name),
            territoryMark = ctx.resources.getString(R.string.def_congregation2_card_mark)
        )
    }

    override fun id() = this.congregationId

    override fun key() = this.congregationNum.hashCode()

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Congregation Entity №").append(congregationNum)
            .append(" '").append(congregationName).append("' [territoryMark = '")
            .append(territoryMark)
            .append("; cLocalitiesId = ").append(cLocalitiesId)
            .append("; isFavorite = ").append(isFavorite)
            .append("'] congregationId = ").append(congregationId)
        return str.toString()
    }
}