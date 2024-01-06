package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryReportDataSource
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TerritoryReportsRepositoryImpl @Inject constructor(
    private val localTerritoryReportDataSource: LocalTerritoryReportDataSource,
    private val mappers: TerritoryReportMappers
) : TerritoryReportsRepository {
    override fun getAll() = localTerritoryReportDataSource.getTerritoryReports()
        .map(mappers.territoryMemberReportEntityListToTerritoryMemberReportsListMapper::map)

    override fun get(territoryReportId: UUID) =
        localTerritoryReportDataSource.getTerritoryReport(territoryReportId)
            .map(mappers.territoryMemberReportEntityToTerritoryMemberReportMapper::map)

    override fun getAllByTerritoryStreet(territoryStreetId: UUID) =
        localTerritoryReportDataSource.getTerritoryStreetReports(territoryStreetId)
            .map(mappers.territoryMemberReportViewListToTerritoryMemberReportsListMapper::map)

    override fun getAllByHouse(houseId: UUID) =
        localTerritoryReportDataSource.getHouseTerritoryReports(houseId)
            .map(mappers.territoryMemberReportViewListToTerritoryMemberReportsListMapper::map)

    override fun getAllByRoom(roomId: UUID) =
        localTerritoryReportDataSource.getRoomTerritoryReports(roomId)
            .map(mappers.territoryMemberReportViewListToTerritoryMemberReportsListMapper::map)

    override fun getTerritoryReportStreet(territoryStreetId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportStreet(territoryStreetId)
            .map(mappers.territoryReportStreetViewToTerritoryReportStreetMapper::map)

    override fun getTerritoryReportStreets(territoryId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportStreets(territoryId)
            .map(mappers.territoryReportStreetViewListToTerritoryReportStreetsListMapper::map)

    override fun getTerritoryReportHouse(houseId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportHouse(houseId)
            .map(mappers.territoryReportHouseViewToTerritoryReportHouseMapper::map)

    override fun getTerritoryReportHouses(territoryId: UUID, territoryStreetId: UUID?) =
        localTerritoryReportDataSource.getTerritoryReportHouses(territoryId, territoryStreetId)
            .map(mappers.territoryReportHouseViewListToTerritoryReportHousesListMapper::map)

    override fun getTerritoryReportRoom(roomId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportRoom(roomId)
            .map(mappers.territoryReportRoomViewToTerritoryReportRoomMapper::map)

    override fun getTerritoryReportRooms(territoryId: UUID, houseId: UUID?) =
        localTerritoryReportDataSource.getTerritoryReportRooms(territoryId, houseId)
            .map(mappers.territoryReportRoomViewListToTerritoryReportRoomsListMapper::map)

    override fun process(territoryReportId: UUID) = flow {
        localTerritoryReportDataSource.process(territoryReportId)
        this.emit(territoryReportId)
    }

    override fun cancelProcess(territoryReportId: UUID) = flow {
        localTerritoryReportDataSource.cancelProcess(territoryReportId)
        this.emit(territoryReportId)
    }

    override fun save(territoryMemberReport: TerritoryMemberReport) = flow {
        if (territoryMemberReport.id == null) {
            localTerritoryReportDataSource.insertTerritoryReport(
                mappers.territoryMemberReportToTerritoryMemberReportEntityMapper.map(
                    territoryMemberReport
                )
            )
        } else {
            localTerritoryReportDataSource.updateTerritoryReport(
                mappers.territoryMemberReportToTerritoryMemberReportEntityMapper.map(
                    territoryMemberReport
                )
            )
        }
        emit(territoryMemberReport)
    }

    override fun save(territoryReportHouse: TerritoryReportHouse) = flow {
        if (territoryReportHouse.territoryMemberReport.id == null) {
            localTerritoryReportDataSource.insertTerritoryReport(
                mappers.territoryReportHouseToTerritoryMemberReportEntityMapper.map(
                    territoryReportHouse
                )
            )
        } else {
            localTerritoryReportDataSource.updateTerritoryReport(
                mappers.territoryReportHouseToTerritoryMemberReportEntityMapper.map(
                    territoryReportHouse
                )
            )
        }
        emit(territoryReportHouse)
    }

    override fun save(territoryReportRoom: TerritoryReportRoom) = flow {
        if (territoryReportRoom.territoryMemberReport.id == null) {
            localTerritoryReportDataSource.insertTerritoryReport(
                mappers.territoryReportRoomToTerritoryMemberReportEntityMapper.map(
                    territoryReportRoom
                )
            )
        } else {
            localTerritoryReportDataSource.updateTerritoryReport(
                mappers.territoryReportRoomToTerritoryMemberReportEntityMapper.map(
                    territoryReportRoom
                )
            )
        }
        emit(territoryReportRoom)
    }

    override fun delete(territoryMemberReport: TerritoryMemberReport) = flow {
        localTerritoryReportDataSource.deleteTerritoryReport(
            mappers.territoryMemberReportToTerritoryMemberReportEntityMapper.map(
                territoryMemberReport
            )
        )
        this.emit(territoryMemberReport)
    }

    override fun deleteById(territoryReportId: UUID) = flow {
        localTerritoryReportDataSource.deleteTerritoryReportById(territoryReportId)
        this.emit(territoryReportId)
    }

    override suspend fun deleteAll() = localTerritoryReportDataSource.deleteAllTerritoryReports()

}