package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMemberReportsUseCase(
    configuration: Configuration, private val territoryReportsRepository: TerritoryReportsRepository
) : UseCase<GetMemberReportsUseCase.Request, GetMemberReportsUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.territoryStreetId) {
        null -> when (request.houseId) {
            null -> when (request.roomId) {
                null -> territoryReportsRepository.getAll()
                else -> territoryReportsRepository.getAllByRoom(request.roomId)
            }

            else -> territoryReportsRepository.getAllByHouse(request.houseId)
        }

        else -> territoryReportsRepository.getAllByTerritoryStreet(request.territoryStreetId)
    }.map {
        Response(it)
    }

    data class Request(
        val territoryStreetId: UUID? = null, val houseId: UUID? = null, val roomId: UUID? = null
    ) : UseCase.Request

    data class Response(val territoryMemberReports: List<TerritoryMemberReport>) : UseCase.Response
}