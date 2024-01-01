package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteMemberReportUseCase(
    configuration: Configuration,
    private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<DeleteMemberReportUseCase.Request, DeleteMemberReportUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return territoryReportsRepository.deleteById(request.territoryMemberReportId)
            .map { Response }
    }

    data class Request(val territoryMemberReportId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
