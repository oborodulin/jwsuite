package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport.TerritoryReportCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryReportMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryReportDataSource
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberReportCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TerritoryReportsRepositoryImpl @Inject constructor(
    private val localTerritoryReportDataSource: LocalTerritoryReportDataSource,
    private val domainMappers: TerritoryReportMappers,
    private val csvMappers: TerritoryReportCsvMappers
) : TerritoryReportsRepository {
    override fun getAll() = localTerritoryReportDataSource.getTerritoryReports()
        .map(domainMappers.territoryMemberReportEntityListToTerritoryMemberReportsListMapper::map)

    override fun get(territoryReportId: UUID) =
        localTerritoryReportDataSource.getTerritoryReport(territoryReportId)
            .map(domainMappers.territoryMemberReportEntityToTerritoryMemberReportMapper::map)

    override fun getAllByTerritoryStreet(territoryStreetId: UUID) =
        localTerritoryReportDataSource.getTerritoryStreetReports(territoryStreetId)
            .map(domainMappers.territoryMemberReportViewListToTerritoryMemberReportsListMapper::map)

    override fun getAllByHouse(houseId: UUID) =
        localTerritoryReportDataSource.getHouseTerritoryReports(houseId)
            .map(domainMappers.territoryMemberReportViewListToTerritoryMemberReportsListMapper::map)

    override fun getAllByRoom(roomId: UUID) =
        localTerritoryReportDataSource.getRoomTerritoryReports(roomId)
            .map(domainMappers.territoryMemberReportViewListToTerritoryMemberReportsListMapper::map)

    override fun getTerritoryReportStreet(territoryStreetId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportStreet(territoryStreetId)
            .map(domainMappers.territoryReportStreetViewToTerritoryReportStreetMapper::map)

    override fun getTerritoryReportStreets(territoryId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportStreets(territoryId)
            .map(domainMappers.territoryReportStreetViewListToTerritoryReportStreetsListMapper::map)

    override fun getTerritoryReportHouse(houseId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportHouse(houseId)
            .map(domainMappers.territoryReportHouseViewToTerritoryReportHouseMapper::map)

    override fun getTerritoryReportHouses(territoryId: UUID, territoryStreetId: UUID?) =
        localTerritoryReportDataSource.getTerritoryReportHouses(territoryId, territoryStreetId)
            .map(domainMappers.territoryReportHouseViewListToTerritoryReportHousesListMapper::map)

    override fun getTerritoryReportRoom(roomId: UUID) =
        localTerritoryReportDataSource.getTerritoryReportRoom(roomId)
            .map(domainMappers.territoryReportRoomViewToTerritoryReportRoomMapper::map)

    override fun getTerritoryReportRooms(territoryId: UUID, houseId: UUID?) =
        localTerritoryReportDataSource.getTerritoryReportRooms(territoryId, houseId)
            .map(domainMappers.territoryReportRoomViewListToTerritoryReportRoomsListMapper::map)

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
                domainMappers.territoryMemberReportToTerritoryMemberReportEntityMapper.map(
                    territoryMemberReport
                )
            )
        } else {
            localTerritoryReportDataSource.updateTerritoryReport(
                domainMappers.territoryMemberReportToTerritoryMemberReportEntityMapper.map(
                    territoryMemberReport
                )
            )
        }
        emit(territoryMemberReport)
    }

    override fun save(territoryReportHouse: TerritoryReportHouse) = flow {
        if (territoryReportHouse.territoryMemberReport.id == null) {
            localTerritoryReportDataSource.insertTerritoryReport(
                domainMappers.territoryReportHouseToTerritoryMemberReportEntityMapper.map(
                    territoryReportHouse
                )
            )
        } else {
            localTerritoryReportDataSource.updateTerritoryReport(
                domainMappers.territoryReportHouseToTerritoryMemberReportEntityMapper.map(
                    territoryReportHouse
                )
            )
        }
        emit(territoryReportHouse)
    }

    override fun save(territoryReportRoom: TerritoryReportRoom) = flow {
        if (territoryReportRoom.territoryMemberReport.id == null) {
            localTerritoryReportDataSource.insertTerritoryReport(
                domainMappers.territoryReportRoomToTerritoryMemberReportEntityMapper.map(
                    territoryReportRoom
                )
            )
        } else {
            localTerritoryReportDataSource.updateTerritoryReport(
                domainMappers.territoryReportRoomToTerritoryMemberReportEntityMapper.map(
                    territoryReportRoom
                )
            )
        }
        emit(territoryReportRoom)
    }

    override fun delete(territoryMemberReport: TerritoryMemberReport) = flow {
        localTerritoryReportDataSource.deleteTerritoryReport(
            domainMappers.territoryMemberReportToTerritoryMemberReportEntityMapper.map(
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

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = TerritoryMemberReportEntity.TABLE_NAME)
    override fun extractTerritoryReports() =
        localTerritoryReportDataSource.getTerritoryReportEntities()
            .map(csvMappers.territoryMemberReportEntityListToTerritoryMemberReportCsvListMapper::map)

    @CsvLoad<TerritoryMemberReportCsv>(
        fileNamePrefix = TerritoryMemberReportEntity.TABLE_NAME,
        contentType = TerritoryMemberReportCsv::class
    )
    override fun loadTerritoryReports(territoryReports: List<TerritoryMemberReportCsv>) = flow {
        localTerritoryReportDataSource.loadTerritoryReportEntities(
            csvMappers.territoryMemberReportCsvListToTerritoryMemberReportEntityListMapper.map(
                territoryReports
            )
        )
        emit(territoryReports.size)
    }
}