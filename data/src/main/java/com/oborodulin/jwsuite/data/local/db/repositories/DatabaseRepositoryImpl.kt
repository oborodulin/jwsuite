package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.DatabaseVersion
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalDatabaseDataSource
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
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
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val localDatabaseDataSource: LocalDatabaseDataSource,
    private val databaseVersion: DatabaseVersion
) : DatabaseRepository {
    override fun transferObjectTableNames(transferObjects: List<Pair<TransferObjectType, Boolean>>) =
        flow {
            val tables = mutableMapOf<String, Boolean>()
            transferObjects.sortedBy { it.second }.forEach { transferObject ->
                transferObjectTables[transferObject.first]?.let { tableNames ->
                    tableNames.filter { it.key == transferObject.second }.map { it.value }
                        .forEach { names -> names.forEach { tables[it] = transferObject.second } }
                }
            }
            emit(tables)
        }

    override fun orderedDataTableNames() = localDatabaseDataSource.getDataTables()

    // API:
    override fun sqliteVersion() = flow { emit(databaseVersion.sqliteVersion) }
    override fun dbVersion() = flow { emit(databaseVersion.dbVersion) }
    override suspend fun checkpoint() = localDatabaseDataSource.setCheckpoint()

    companion object {
        //Common:
        private val localityTables = listOf(
            GeoRegionEntity.TABLE_NAME,
            GeoRegionTlEntity.TABLE_NAME,
            GeoRegionDistrictEntity.TABLE_NAME,
            GeoRegionDistrictTlEntity.TABLE_NAME,
            GeoLocalityEntity.TABLE_NAME,
            GeoLocalityTlEntity.TABLE_NAME
        )
        private val geoTables = localityTables + listOf(
            GeoLocalityDistrictEntity.TABLE_NAME,
            GeoLocalityDistrictTlEntity.TABLE_NAME,
            GeoMicrodistrictEntity.TABLE_NAME,
            GeoMicrodistrictTlEntity.TABLE_NAME,
            GeoStreetEntity.TABLE_NAME,
            GeoStreetTlEntity.TABLE_NAME,
            GeoStreetDistrictEntity.TABLE_NAME
        )

        //Favorite congregation:
        private val congregationTables = listOf(CongregationEntity.TABLE_NAME)
        private val memberTables = listOf(
            GroupEntity.TABLE_NAME,
            MemberEntity.TABLE_NAME,
            MemberCongregationCrossRefEntity.TABLE_NAME,
            MemberMovementEntity.TABLE_NAME,
            MemberRoleEntity.TABLE_NAME
        )
        private val territoryTables = listOf(
            TerritoryStreetEntity.TABLE_NAME,
            HouseEntity.TABLE_NAME,
            EntranceEntity.TABLE_NAME,
            FloorEntity.TABLE_NAME,
            RoomEntity.TABLE_NAME,
            TerritoryCategoryEntity.TABLE_NAME,
            TerritoryEntity.TABLE_NAME,
            CongregationTerritoryCrossRefEntity.TABLE_NAME,
            TerritoryMemberCrossRefEntity.TABLE_NAME,
            TerritoryMemberReportEntity.TABLE_NAME
        )
        private val totalsTables = listOf(CongregationTotalEntity.TABLE_NAME)

        //Strict usage:
        private val strictMemberTables = listOf(
            GroupEntity.TABLE_NAME,
            MemberEntity.TABLE_NAME,
            MemberCongregationCrossRefEntity.TABLE_NAME,
            MemberRoleEntity.TABLE_NAME
        )

        //Personal usage:
        private val personalTerritoryReportTables = listOf(
            TerritoryMemberReportEntity.TABLE_NAME
        )

        val transferObjectTables =
            mapOf(
                TransferObjectType.ALL to mapOf(false to geoTables + congregationTables + memberTables + territoryTables + totalsTables),
                TransferObjectType.MEMBERS to mapOf(
                    false to localityTables + congregationTables + memberTables,
                    true to localityTables + congregationTables + strictMemberTables
                ),
                TransferObjectType.TERRITORIES to mapOf(
                    false to geoTables + congregationTables + strictMemberTables + territoryTables,
                    true to geoTables + territoryTables
                ),
                TransferObjectType.TERRITORY_REPORT to mapOf(
                    true to personalTerritoryReportTables
                ),
                TransferObjectType.BILLS to mapOf(
                    false to listOf(AppSettingEntity.TABLE_NAME),
                    true to geoTables + territoryTables
                ),
                TransferObjectType.REPORTS to mapOf(false to localityTables + congregationTables + totalsTables)
            )
    }
}