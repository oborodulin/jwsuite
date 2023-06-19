package com.oborodulin.jwsuite.data.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.util.VillageType
import java.util.UUID

@Entity(
    tableName = GeoMicrodistrictEntity.TABLE_NAME,
    indices = [Index(
        value = ["localitiesId", "localityDistrictsId", "villageType", "villageShortName"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("localityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"),
        childColumns = arrayOf("localitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class GeoMicrodistrictEntity(
    @PrimaryKey val microdistrictId: UUID = UUID.randomUUID(),
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String,
    @ColumnInfo(index = true) val localityDistrictsId: UUID,
    @ColumnInfo(index = true) val localitiesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_microdistricts"

        fun defaultMicrodistrict(
            localityId: UUID = UUID.randomUUID(), localityDistrictId: UUID = UUID.randomUUID(),
            microdistrictId: UUID = UUID.randomUUID(),
            microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
            microdistrictShortName: String
        ) = GeoMicrodistrictEntity(
            localitiesId = localityId, localityDistrictsId = localityDistrictId,
            microdistrictId = microdistrictId, microdistrictType = microdistrictType,
            microdistrictShortName = microdistrictShortName
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
        var result = localitiesId.hashCode()
        result = result * 31 + microdistrictType.hashCode()
        result = result * 31 + microdistrictShortName.hashCode()
        localityDistrictsId?.let { result = result * 31 + it.hashCode() }
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Microdistrict Entity ").append(microdistrictType).append(" '")
            .append(microdistrictShortName).append("'")
            .append(" [localitiesId = ").append(localitiesId)
        localityDistrictsId?.let { str.append("; localityDistrictsId = ").append(it) }
        str.append("] villageId = ").append(microdistrictId)
        return str.toString()
    }
}