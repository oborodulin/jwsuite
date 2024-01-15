package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryTotalEntity
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.coroutines.flow.flow

class DatabaseRepositoryImpl : DatabaseRepository {
    override fun transferObjectTables(transferObjects: List<TransferObjectType>) = flow {
        val tables = mutableSetOf<String>()
        transferObjects.forEach { transferObject ->
            transferObjectTables[transferObject]?.let {
                tables.addAll(it)
            }
        }
        emit(tables.toList())
    }

    override fun tablesByOrder() = flow { emit(tablesOrder) }

    companion object {
        val tablesOrder = listOf(
            AppSettingEntity.TABLE_NAME,
            // Geo
            GeoRegionEntity.TABLE_NAME,
            GeoRegionTlEntity.TABLE_NAME,
            GeoRegionDistrictEntity.TABLE_NAME,
            GeoRegionDistrictTlEntity.TABLE_NAME,
            GeoLocalityEntity.TABLE_NAME,
            GeoLocalityTlEntity.TABLE_NAME,
            GeoLocalityDistrictEntity.TABLE_NAME,
            GeoLocalityDistrictTlEntity.TABLE_NAME,
            GeoMicrodistrictEntity.TABLE_NAME,
            GeoMicrodistrictTlEntity.TABLE_NAME,
            GeoStreetEntity.TABLE_NAME,
            GeoStreetTlEntity.TABLE_NAME,
            GeoStreetDistrictEntity.TABLE_NAME,
            // Congregation
            CongregationEntity.TABLE_NAME,
            GroupEntity.TABLE_NAME,
            MemberEntity.TABLE_NAME,
            MemberCongregationCrossRefEntity.TABLE_NAME,
            MemberMovementEntity.TABLE_NAME,
            RoleEntity.TABLE_NAME,
            MemberRoleEntity.TABLE_NAME,
            RoleTransferObjectEntity.TABLE_NAME,
            TransferObjectEntity.TABLE_NAME,
            CongregationTotalEntity.TABLE_NAME,
            // Territory
            TerritoryCategoryEntity.TABLE_NAME,
            TerritoryEntity.TABLE_NAME,
            CongregationTerritoryCrossRefEntity.TABLE_NAME,
            TerritoryMemberCrossRefEntity.TABLE_NAME,
            TerritoryStreetEntity.TABLE_NAME,
            TerritoryMemberReportEntity.TABLE_NAME,
            HouseEntity.TABLE_NAME,
            EntranceEntity.TABLE_NAME,
            FloorEntity.TABLE_NAME,
            RoomEntity.TABLE_NAME,
            TerritoryTotalEntity.TABLE_NAME
        )
        val transferObjectTables = mapOf(
            TransferObjectType.ALL to listOf(
                AppSettingEntity.TABLE_NAME,
            ),
            TransferObjectType.MEMBERS to listOf(
                AppSettingEntity.TABLE_NAME,
            ),
            TransferObjectType.TERRITORIES to listOf(
                AppSettingEntity.TABLE_NAME,
            ),
            TransferObjectType.BILLS to listOf(
                AppSettingEntity.TABLE_NAME,
            )
        )
    }
}