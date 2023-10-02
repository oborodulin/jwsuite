package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import kotlinx.serialization.Serializable
import java.util.Locale
import java.util.UUID

@Entity(
    tableName = GeoMicrodistrictTlEntity.TABLE_NAME,
    indices = [Index(value = ["microdistrictsId", "microdistrictLocCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoMicrodistrictEntity::class,
        parentColumns = arrayOf("microdistrictId"),
        childColumns = arrayOf("microdistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoMicrodistrictTlEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val microdistrictTlId: UUID = UUID.randomUUID(),
    val microdistrictLocCode: String = Locale.getDefault().language,
    val microdistrictName: String,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val microdistrictsId: UUID,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_microdistricts_tl"

        fun defaultMicrodistrictTl(
            microdistrictTlId: UUID = UUID.randomUUID(), microdistrictId: UUID,
            microdistrictName: String
        ) = GeoMicrodistrictTlEntity(
            microdistrictTlId = microdistrictTlId, microdistrictsId = microdistrictId,
            microdistrictName = microdistrictName
        )

        fun cvetochnyMicrodistrictTl(ctx: Context, microdistrictId: UUID) = defaultMicrodistrictTl(
            microdistrictName = ctx.resources.getString(R.string.def_cvetochny_name),
            microdistrictId = microdistrictId
        )

        fun donskoyMicrodistrictTl(ctx: Context, microdistrictId: UUID) = defaultMicrodistrictTl(
            microdistrictName = ctx.resources.getString(R.string.def_don_name),
            microdistrictId = microdistrictId
        )

        fun microdistrictTl(ctx: Context, microdistrictShortName: String, microdistrictId: UUID) =
            when (microdistrictShortName) {
                ctx.resources.getString(R.string.def_cvetochny_short_name) -> cvetochnyMicrodistrictTl(
                    ctx, microdistrictId
                )

                ctx.resources.getString(R.string.def_don_short_name) -> donskoyMicrodistrictTl(
                    ctx, microdistrictId
                )

                else -> defaultMicrodistrictTl(
                    microdistrictId = UUID.randomUUID(),
                    microdistrictName = ""
                )
            }
    }

    override fun id() = this.microdistrictTlId

    override fun key(): Int {
        var result = microdistrictsId.hashCode()
        result = result * 31 + microdistrictLocCode.hashCode()
        return result
    }
}


