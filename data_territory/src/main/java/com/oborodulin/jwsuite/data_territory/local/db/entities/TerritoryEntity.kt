package com.oborodulin.jwsuite.data_territory.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = TerritoryEntity.TABLE_NAME,
    indices = [Index(
        value = ["tCongregationsId", "tTerritoryCategoriesId", "tLocalitiesId", "tLocalityDistrictsId", "tMicrodistrictsId", "territoryNum"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = GeoMicrodistrictEntity::class,
        parentColumns = arrayOf("microdistrictId"), childColumns = arrayOf("tMicrodistrictsId"),
        onDelete = ForeignKey.CASCADE, deferred = true
    ), ForeignKey(
        entity = GeoLocalityDistrictEntity::class,
        parentColumns = arrayOf("localityDistrictId"),
        childColumns = arrayOf("tLocalityDistrictsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = GeoLocalityEntity::class,
        parentColumns = arrayOf("localityId"), childColumns = arrayOf("tLocalitiesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TerritoryCategoryEntity::class,
        parentColumns = arrayOf("territoryCategoryId"),
        childColumns = arrayOf("tTerritoryCategoriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"), childColumns = arrayOf("tCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class TerritoryEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val territoryId: UUID = UUID.randomUUID(),
    val territoryNum: Int,
    val isActive: Boolean = true,
    val isBusinessTerritory: Boolean = false,
    val isGroupMinistry: Boolean = false,
    //val isInPerimeter: Boolean = false, // need ones table perimeter_streets
    val isProcessed: Boolean = true, // for isGroupMinistry
    val territoryDesc: String? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val tMicrodistrictsId: UUID? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val tLocalityDistrictsId: UUID? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val tLocalitiesId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val tTerritoryCategoriesId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val tCongregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "territories"
        const val PX = "t_"

        // Regions:
        const val PX_REGION = "tr_"
        const val PX_LD_REGION = "tlr_"
        const val PX_M_REGION = "tmr_"

        // Region Districts:
        const val PX_REGION_DISTRICT = "trd_"
        const val PX_LD_REGION_DISTRICT = "tlrd_"
        const val PX_M_REGION_DISTRICT = "tmrd_"

        // Localities:
        const val PX_LOCALITY = "tl_"
        const val PX_LD_LOCALITY = "tll_"
        const val PX_M_LOCALITY = "tml_"

        // Locality Districts:
        const val PX_LD_LOCALITY_DISTRICT = "tlld_"
        const val PX_M_LOCALITY_DISTRICT = "tmld_"

        // Microdistricts:
        const val PX_MICRODISTRICT = "tm_"

        fun defaultTerritory(
            territoryId: UUID = UUID.randomUUID(),
            congregationId: UUID = UUID.randomUUID(), territoryCategoryId: UUID = UUID.randomUUID(),
            localityId: UUID = UUID.randomUUID(), localityDistrictId: UUID? = null,
            microdistrictId: UUID? = null,
            territoryNum: Int,
            isBusiness: Boolean = false, //isInPerimeter: Boolean = false,
            isGroupMinistry: Boolean = false,
            isProcessed: Boolean = true, isActive: Boolean = true, territoryDesc: String? = null
        ) = TerritoryEntity(
            tCongregationsId = congregationId, tTerritoryCategoriesId = territoryCategoryId,
            tLocalitiesId = localityId, tLocalityDistrictsId = localityDistrictId,
            tMicrodistrictsId = microdistrictId,
            territoryId = territoryId,
            territoryNum = territoryNum, isBusinessTerritory = isBusiness,
            isGroupMinistry = isGroupMinistry, //isInPerimeter = isInPerimeter,
            isProcessed = isProcessed, isActive = isActive, territoryDesc = territoryDesc
        )

        fun businessTerritory(
            congregationId: UUID = UUID.randomUUID(), territoryCategoryId: UUID = UUID.randomUUID(),
            localityId: UUID = UUID.randomUUID(), localityDistrictId: UUID? = null,
            microdistrictId: UUID? = null,
            territoryNum: Int, territoryDesc: String? = null
        ) = defaultTerritory(
            congregationId = congregationId, territoryCategoryId = territoryCategoryId,
            localityId = localityId, localityDistrictId = localityDistrictId,
            microdistrictId = microdistrictId,
            territoryNum = territoryNum, isBusiness = true,
            territoryDesc = territoryDesc
        )
    }

    override fun id() = this.territoryId

    // https://www.baeldung.com/java-hashcode
    override fun key(): Int {
        var result = tCongregationsId.hashCode()
        result = result * 31 + tTerritoryCategoriesId.hashCode()
        result = result * 31 + tLocalitiesId.hashCode()
        result = result * 31 + territoryNum.hashCode()
        result = result * 31 + tLocalityDistrictsId.hashCode()
        result = result * 31 + tMicrodistrictsId.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Entity №").append(territoryNum)
            .append(" [tCongregationsId = ").append(tCongregationsId)
            .append("; isActive = ").append(isActive)
            .append("; isBusinessTerritory = ").append(isBusinessTerritory)
            .append("; isGroupMinistry = ").append(isGroupMinistry)
            //.append("; isInPerimeter = ").append(isInPerimeter)
            .append("; isProcessed = ").append(isProcessed)
        tLocalityDistrictsId?.let { str.append("; tLocalityDistrictsId = ").append(it) }
        tMicrodistrictsId?.let { str.append("; tMicrodistrictsId = ").append(it) }
        str.append("; tLocalitiesId = ").append(tLocalitiesId)
            .append("; tTerritoryCategoriesId = ").append(tTerritoryCategoriesId)
            .append("] territoryId = ").append(territoryId)
        return str.toString()
    }
}