package com.oborodulin.jwsuite.data_territory.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryTotalEntity
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryDataSource
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalTerritoryDataSourceImpl @Inject constructor(
    private val territoryDao: TerritoryDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalTerritoryDataSource {
    // Territories:
    override fun getCongregationTerritories(congregationId: UUID) =
        territoryDao.findByCongregationId(congregationId)

    override fun getFavoriteCongregationTerritories() = territoryDao.findByFavoriteCongregation()

    override fun getCongregationTerritoryLocations(
        isPrivateSector: Boolean,
        congregationId: UUID?
    ) = territoryDao.findTerritoryLocationsByPrivateSectorMarkAndCongregationId(
        isPrivateSector, congregationId
    )

    override fun getHandOutTerritories(
        congregationId: UUID?, isPrivateSector: Boolean?,
        territoryLocationType: TerritoryLocationType, locationId: UUID?
    ) = territoryDao.findHandOutTerritories(
        congregationId, isPrivateSector, territoryLocationType, locationId
    )

    override fun getAtWorkTerritories(
        congregationId: UUID?, isPrivateSector: Boolean?,
        territoryLocationType: TerritoryLocationType, locationId: UUID?
    ) = territoryDao.findAtWorkTerritories(
        congregationId, isPrivateSector, territoryLocationType, locationId
    )

    override fun getIdleTerritories(
        congregationId: UUID?, isPrivateSector: Boolean?,
        territoryLocationType: TerritoryLocationType, locationId: UUID?
    ) = territoryDao.findIdleTerritories(
        congregationId, isPrivateSector, territoryLocationType, locationId
    )

    override fun getTerritoriesByGeo(
        localityId: UUID, localityDistrictId: UUID?, microdistrictId: UUID?
    ) = territoryDao.findByLocalityIdAndLocalityDistrictIdAndMicrodistrictId(
        localityId, localityDistrictId, microdistrictId
    )

    override fun getTerritoriesForHouse(houseId: UUID) = territoryDao.findByHouseId(houseId)

    override fun getNextTerritoryNum(congregationId: UUID, territoryCategoryId: UUID) =
        territoryDao.nextTerritoryNum(congregationId, territoryCategoryId)

    //override fun getTerritoryInfo(territoryId: UUID) = territoryDao.findInfoByTerritoryId(territoryId)

    override fun getTerritory(territoryId: UUID) = territoryDao.findDistinctById(territoryId)

    override suspend fun insertTerritory(territory: TerritoryEntity) = withContext(dispatcher) {
        territoryDao.insertWithTerritoryNum(territory)
    }

    override suspend fun updateTerritory(territory: TerritoryEntity) = withContext(dispatcher) {
        territoryDao.updateWithTerritoryNum(territory)
    }

    override suspend fun deleteTerritory(territory: TerritoryEntity) = withContext(dispatcher) {
        territoryDao.delete(territory)
    }

    override suspend fun deleteTerritoryById(territoryId: UUID) = withContext(dispatcher) {
        territoryDao.deleteById(territoryId)
    }

    override suspend fun deleteTerritories(territories: List<TerritoryEntity>) =
        withContext(dispatcher) { territoryDao.delete(territories) }

    override suspend fun deleteAllTerritories() = withContext(dispatcher) {
        territoryDao.deleteAll()
    }

    // Congregations:
    override suspend fun insertTerritoryToCongregation(
        congregation: CongregationEntity, territory: TerritoryEntity, startUsingDate: OffsetDateTime
    ) = withContext(dispatcher) {
        territoryDao.insert(congregation, territory, startUsingDate)
    }

    override suspend fun updateTerritoryInCongregation(congregationTerritory: CongregationTerritoryCrossRefEntity) =
        withContext(dispatcher) {
            territoryDao.update(congregationTerritory)
        }

    override suspend fun deleteTerritoryFromCongregation(congregationTerritory: CongregationTerritoryCrossRefEntity) =
        withContext(dispatcher) {
            territoryDao.deleteTerritory(congregationTerritory)
        }

    override suspend fun deleteTerritoryFromCongregation(congregationTerritoryId: UUID) =
        withContext(dispatcher) {
            territoryDao.deleteTerritoryById(congregationTerritoryId)
        }

    override suspend fun deleteAllTerritoriesFromCongregation(congregationId: UUID) =
        withContext(dispatcher) {
            territoryDao.deleteTerritoriesByCongregationId(congregationId)
        }

    // Members:
    override suspend fun insertMember(
        territory: TerritoryEntity, member: MemberEntity, receivingDate: OffsetDateTime
    ) = withContext(dispatcher) { territoryDao.insert(territory, member, receivingDate) }

    override suspend fun insertMember(
        territory: TerritoryEntity, memberId: UUID, receivingDate: OffsetDateTime
    ) = withContext(dispatcher) { territoryDao.insert(territory, memberId, receivingDate) }

    override suspend fun insertMember(
        territoryId: UUID, memberId: UUID, receivingDate: OffsetDateTime
    ) = withContext(dispatcher) { territoryDao.insert(territoryId, memberId, receivingDate) }

    override suspend fun updateMember(territoryMember: TerritoryMemberCrossRefEntity) =
        withContext(dispatcher) { territoryDao.update(territoryMember) }

    override suspend fun deleteMember(territoryMember: TerritoryMemberCrossRefEntity) =
        withContext(dispatcher) { territoryDao.deleteMember(territoryMember) }

    override suspend fun deleteMember(territoryMemberId: UUID) = withContext(dispatcher) {
        territoryDao.deleteMemberById(territoryMemberId)
    }

    override suspend fun deleteMembers(territoryId: UUID) = withContext(dispatcher) {
        territoryDao.deleteMembersByTerritoryId(territoryId)
    }

    // API:
    override suspend fun handOut(
        territoryId: UUID, memberId: UUID, receivingDate: OffsetDateTime
    ) = withContext(dispatcher) { territoryDao.handOut(territoryId, memberId, receivingDate) }

    override suspend fun process(territoryId: UUID, deliveryDate: OffsetDateTime) =
        withContext(dispatcher) { territoryDao.process(territoryId, deliveryDate) }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getTerritoryEntities() = territoryDao.selectEntities()
    override fun getCongregationTerritoryEntities() =
        territoryDao.selectCongregationTerritoryEntities()

    override fun getTerritoryMemberEntities() = territoryDao.selectTerritoryMemberEntities()
    override fun getTerritoryTotalEntities() = territoryDao.selectTotalEntities()

    override suspend fun loadTerritoryEntities(territories: List<TerritoryEntity>) =
        withContext(dispatcher) {
            territoryDao.insert(territories)
        }

    override suspend fun loadCongregationTerritoryEntities(congregationTerritories: List<CongregationTerritoryCrossRefEntity>) =
        withContext(dispatcher) {
            territoryDao.insert(congregationTerritories)
        }

    override suspend fun loadTerritoryMemberEntities(territoryMembers: List<TerritoryMemberCrossRefEntity>) =
        withContext(dispatcher) {
            territoryDao.insert(territoryMembers)
        }

    override suspend fun loadTerritoryTotalEntities(territoryTotals: List<TerritoryTotalEntity>) =
        withContext(dispatcher) {
            territoryDao.insert(territoryTotals)
        }
}
