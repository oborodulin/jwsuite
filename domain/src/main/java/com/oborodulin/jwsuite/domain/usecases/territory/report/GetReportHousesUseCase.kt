package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetReportHousesUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<GetReportHousesUseCase.Request, GetReportHousesUseCase.Response>(configuration) {
    override fun process(request: Request) = territoryReportsRepository.getTerritoryReportHouses(
        request.territoryId,
        request.territoryStreetId
    ).map {
        Response(it)
    }

    data class Request(val territoryId: UUID, val territoryStreetId: UUID? = null) : UseCase.Request
    data class Response(val territoryReportHouses: List<TerritoryReportHouse>) : UseCase.Response
}