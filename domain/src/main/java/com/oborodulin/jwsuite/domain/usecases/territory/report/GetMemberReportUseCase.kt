package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMemberReportUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<GetMemberReportUseCase.Request, GetMemberReportUseCase.Response>(configuration) {
    override fun process(request: Request) =
        territoryReportsRepository.get(request.territoryReportId).map {
            Response(it)
        }

    data class Request(val territoryReportId: UUID) : UseCase.Request
    data class Response(val territoryMemberReport: TerritoryMemberReport) : UseCase.Response
}