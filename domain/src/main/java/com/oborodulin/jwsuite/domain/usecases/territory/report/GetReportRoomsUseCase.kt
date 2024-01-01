package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetReportRoomsUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<GetReportRoomsUseCase.Request, GetReportRoomsUseCase.Response>(configuration) {
    override fun process(request: Request) = territoryReportsRepository.getTerritoryReportRooms(
        request.territoryId,
        request.houseId
    ).map {
        Response(it)
    }

    data class Request(val territoryId: UUID, val houseId: UUID? = null) : UseCase.Request
    data class Response(val territoryReportRooms: List<TerritoryReportRoom>) : UseCase.Response
}