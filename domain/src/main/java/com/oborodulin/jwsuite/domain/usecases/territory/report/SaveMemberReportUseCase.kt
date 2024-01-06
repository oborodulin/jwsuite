package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveMemberReportUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<SaveMemberReportUseCase.Request, SaveMemberReportUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return territoryReportsRepository.save(request.territoryMemberReport)
            .map {
                Response(it)
            }.catch { throw UseCaseException.MemberReportSaveException(it) }
    }

    data class Request(val territoryMemberReport: TerritoryMemberReport) : UseCase.Request
    data class Response(val territoryMemberReport: TerritoryMemberReport) : UseCase.Response
}
