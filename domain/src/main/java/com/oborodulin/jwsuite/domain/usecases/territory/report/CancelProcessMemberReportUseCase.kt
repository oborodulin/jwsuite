package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class CancelProcessMemberReportUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<CancelProcessMemberReportUseCase.Request, CancelProcessMemberReportUseCase.Response>(
    configuration
) {
    override fun process(request: Request) =
        territoryReportsRepository.cancelProcess(request.territoryReportId).map {
            Response
        }

    data class Request(val territoryReportId: UUID) : UseCase.Request
    data object Response : UseCase.Response
}