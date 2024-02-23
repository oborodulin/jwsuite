package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.types.VillageType
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = GeoMicrodistrictEntity.TABLE_NAME,
    indices = [Index(
        value = ["mLocalitiesId", "mLocalityDistrictsId", "microdistrictType", "microdistrictOsmId", "microdistrictShortName"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("mLocalityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("mLocalitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoMicrodistrictEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val microdistrictId: UUID = UUID.randomUUID(),
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String,
    @ColumnInfo(index = true) val microdistrictOsmId: Long? = null,
    @Embedded(prefix = PREFIX) val coordinates: Coordinates? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mLocalityDistrictsId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mLocalitiesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_microdistricts"
        const val PREFIX = "microdistrict_"
        const val PX = "md_"

        fun defaultMicrodistrict(
            localityId: UUID = UUID.randomUUID(), localityDistrictId: UUID = UUID.randomUUID(),
            microdistrictId: UUID = UUID.randomUUID(),
            microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
            microdistrictShortName: String,
            microdistrictOsmId: Long? = null, coordinates: Coordinates? = null
        ) = GeoMicrodistrictEntity(
            mLocalitiesId = localityId, mLocalityDistrictsId = localityDistrictId,
            microdistrictId = microdistrictId, microdistrictType = microdistrictType,
            microdistrictShortName = microdistrictShortName,
            microdistrictOsmId = microdistrictOsmId, coordinates = coordinates
        )

        fun cvetochnyMicrodistrict(ctx: Context, localityId: UUID, localityDistrictId: UUID) =
            defaultMicrodistrict(
                microdistrictType = VillageType.MICRO_DISTRICT,
                microdistrictShortName = ctx.resources.getString(R.string.def_cvetochny_short_name),
                localityDistrictId = localityDistrictId,
                localityId = localityId
            )

        fun donskoyMicrodistrict(ctx: Context, localityId: UUID, localityDistrictId: UUID) =
            defaultMicrodistrict(
                microdistrictType = VillageType.MICRO_DISTRICT,
                microdistrictShortName = ctx.resources.getString(R.string.def_don_short_name),
                localityDistrictId = localityDistrictId,
                localityId = localityId
            )

    }

    override fun id() = this.microdistrictId

    override fun key(): Int {
        var result = mLocalitiesId.hashCode()
        result = result * 31 + microdistrictType.hashCode()
        result = result * 31 + microdistrictOsmId.hashCode()
        result = result * 31 + microdistrictShortName.hashCode()
        result = result * 31 + mLocalityDistrictsId.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Microdistrict Entity ").append(microdistrictType).append(" '")
            .append(microdistrictShortName)
            .append("'. OSM: microdistrictOsmId = ").append(microdistrictOsmId)
            .append("; coordinates = ").append(coordinates)
            .append(" [mLocalitiesId = ").append(mLocalitiesId)
            .append("; mLocalityDistrictsId = ").append(mLocalityDistrictsId)
            .append("] microdistrictId = ").append(microdistrictId)
        return str.toString()
    }
}