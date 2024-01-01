package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveReportRoomUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<SaveReportRoomUseCase.Request, SaveReportRoomUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return territoryReportsRepository.save(request.territoryReportRoom)
            .map {
                Response(it)
            }.catch { throw UseCaseException.ReportRoomSaveException(it) }
    }

    data class Request(val territoryReportRoom: TerritoryReportRoom) : UseCase.Request
    data class Response(val territoryReportRoom: TerritoryReportRoom) : UseCase.Response
}
