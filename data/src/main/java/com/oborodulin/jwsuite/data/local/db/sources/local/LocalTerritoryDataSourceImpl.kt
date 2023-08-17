package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.TerritoryDao
import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.util.*
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

    //override fun getTerritoryInfo(territoryId: UUID) = territoryDao.findInfoByTerritoryId(territoryId)

    override fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID?) =
        territoryDao.findStreetNamesAndHouseNumsByCongregationId(congregationId)

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

    override suspend fun deleteAllTerritoriesFromCongregation(congregationId: UUID) = withContext(dispatcher) {
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

    // Streets:
    override fun getTerritoryStreets(territoryId: UUID) =
        territoryDao.findByTerritoryId(territoryId)

    /*    override fun getTerritoryStreetNames(territoryId: UUID) =
            territoryDao.findNamesByTerritoryId(territoryId)*/

    override suspend fun insertStreet(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEven: Boolean?, isPrivateSector: Boolean?, estimatedHouses: Int?
    ) = withContext(dispatcher) {
        territoryDao.insert(territory, street, isEven, isPrivateSector, estimatedHouses)
    }

    override suspend fun updateStreet(territoryStreet: TerritoryStreetEntity) =
        withContext(dispatcher) { territoryDao.update(territoryStreet) }

    override suspend fun deleteStreet(territoryStreet: TerritoryStreetEntity) =
        withContext(dispatcher) { territoryDao.deleteStreet(territoryStreet) }

    override suspend fun deleteStreet(territoryStreetId: UUID) = withContext(dispatcher) {
        territoryDao.deleteStreetById(territoryStreetId)
    }

    override suspend fun deleteStreets(territoryId: UUID) = withContext(dispatcher) {
        territoryDao.deleteStreetsByTerritoryId(territoryId)
    }

    // API:
    override suspend fun handOut(
        territoryId: UUID, memberId: UUID, receivingDate: OffsetDateTime
    ) = withContext(dispatcher) { territoryDao.handOut(territoryId, memberId, receivingDate) }
}
