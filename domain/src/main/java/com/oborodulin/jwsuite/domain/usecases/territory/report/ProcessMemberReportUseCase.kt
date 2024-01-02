package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class ProcessMemberReportUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<ProcessMemberReportUseCase.Request, ProcessMemberReportUseCase.Response>(configuration) {
    override fun process(request: Request) =
        territoryReportsRepository.process(request.territoryReportId).map {
            Response
        }

    data class Request(val territoryReportId: UUID) : UseCase.Request
    data object Response : UseCase.Response
}