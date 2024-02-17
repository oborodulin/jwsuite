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
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
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
@Serializable
data class CongregationEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val congregationId: UUID = UUID.randomUUID(),
    val congregationNum: String,
    val congregationName: String,
    val territoryMark: String,
    val isFavorite: Boolean = false,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val lastVisitDate: OffsetDateTime? = null,
    @Serializable(with = UUIDSerializer::class)
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

        fun defCongregation(ctx: Context, localityId: UUID) = defaultCongregation(
            localityId = localityId,
            congregationNum = ctx.resources.getString(R.string.def_congregation_num),
            congregationName = ctx.resources.getString(R.string.def_congregation_name),
            territoryMark = ctx.resources.getString(R.string.def_congregation_card_mark),
            isFavorite = true
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
            .append(" '").append(congregationName).append("' territoryMark = '")
            .append(territoryMark).append("'")
        lastVisitDate?.let {
            str.append(". Date of last visit ").append(DateTimeFormatter.ISO_LOCAL_DATE.format(it))
        }
        str.append(" [cLocalitiesId = ").append(cLocalitiesId)
            .append("; isFavorite = ").append(isFavorite)
            .append("'] congregationId = ").append(congregationId)
        return str.toString()
    }
}