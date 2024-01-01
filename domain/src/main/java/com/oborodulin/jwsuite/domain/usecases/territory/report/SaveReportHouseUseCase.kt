package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveReportHouseUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<SaveReportHouseUseCase.Request, SaveReportHouseUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return territoryReportsRepository.save(request.territoryReportHouse)
            .map {
                Response(it)
            }.catch { throw UseCaseException.ReportHouseSaveException(it) }
    }

    data class Request(val territoryReportHouse: TerritoryReportHouse) : UseCase.Request
    data class Response(val territoryReportHouse: TerritoryReportHouse) : UseCase.Response
}
