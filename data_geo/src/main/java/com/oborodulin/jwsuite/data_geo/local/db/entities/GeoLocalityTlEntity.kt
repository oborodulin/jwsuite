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
    tableName = GeoLocalityTlEntity.TABLE_NAME,
    indices = [Index(value = ["localitiesId", "localityLocCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("localitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoLocalityTlEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val localityTlId: UUID = UUID.randomUUID(),
    val localityLocCode: String = Locale.getDefault().language,
    val localityShortName: String,
    val localityName: String,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val localitiesId: UUID,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_localities_tl"

        fun defaultLocalityTl(
            localityTlId: UUID = UUID.randomUUID(), localityId: UUID,
            localityShortName: String, localityName: String
        ) = GeoLocalityTlEntity(
//            localityType = ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.locality_types)[0],
            localityTlId = localityTlId, localitiesId = localityId,
            localityShortName = localityShortName, localityName = localityName
        )

        fun donetskLocalityTl(ctx: Context, localityId: UUID) = defaultLocalityTl(
            localityShortName = ctx.resources.getString(R.string.def_donetsk_short_name),
            localityName = ctx.resources.getString(R.string.def_donetsk_name),
            localityId = localityId
        )

        fun makeevkaLocalityTl(ctx: Context, localityId: UUID) = defaultLocalityTl(
            localityShortName = ctx.resources.getString(R.string.def_makeevka_short_name),
            localityName = ctx.resources.getString(R.string.def_makeevka_name),
            localityId = localityId
        )

        fun luganskLocalityTl(ctx: Context, localityId: UUID) = defaultLocalityTl(
            localityShortName = ctx.resources.getString(R.string.def_luhansk_short_name),
            localityName = ctx.resources.getString(R.string.def_luhansk_name),
            localityId = localityId
        )

        fun marinkaLocalityTl(ctx: Context, localityId: UUID) = defaultLocalityTl(
            localityShortName = ctx.resources.getString(R.string.def_marinka_short_name),
            localityName = ctx.resources.getString(R.string.def_marinka_name),
            localityId = localityId
        )

        fun mospinoLocalityTl(ctx: Context, localityId: UUID) = defaultLocalityTl(
            localityShortName = ctx.resources.getString(R.string.def_mospino_short_name),
            localityName = ctx.resources.getString(R.string.def_mospino_name),
            localityId = localityId
        )

        fun localityTl(ctx: Context, localityCode: String, localityId: UUID) =
            when (localityCode) {
                ctx.resources.getString(R.string.def_donetsk_code) -> donetskLocalityTl(
                    ctx, localityId
                )

                ctx.resources.getString(R.string.def_makeevka_code) -> makeevkaLocalityTl(
                    ctx, localityId
                )

                ctx.resources.getString(R.string.def_luhansk_code) -> luganskLocalityTl(
                    ctx, localityId
                )

                ctx.resources.getString(R.string.def_marinka_code) -> marinkaLocalityTl(
                    ctx, localityId
                )

                ctx.resources.getString(R.string.def_mospino_code) -> mospinoLocalityTl(
                    ctx, localityId
                )

                else -> defaultLocalityTl(
                    localityId = UUID.randomUUID(), localityShortName = "", localityName = ""
                )
            }
    }

    override fun id() = this.localityTlId

    override fun key(): Int {
        var result = localitiesId.hashCode()
        result = result * 31 + localityLocCode.hashCode()
        return result
    }
}


