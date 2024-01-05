package com.oborodulin.jwsuite.domain.usecases.territory.report

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMemberReportUseCase(
    configuration: Configuration,
    private val territoryReportsRepository: TerritoryReportsRepository,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetMemberReportUseCase.Request, GetMemberReportUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.territoryReportId) {
        null -> when (request.territoryStreetId) {
            null -> when (request.houseId) {
                null -> territoryReportsRepository.getTerritoryReportRoom(request.roomId!!).map {
                    Response(
                        TerritoryMemberReport(
                            room = it.room,
                            territoryMemberId = it.territoryMemberReport.territoryMemberId
                        )
                    )
                }

                else -> territoryReportsRepository.getTerritoryReportHouse(request.houseId).map {
                    Response(
                        TerritoryMemberReport(
                            house = it.house,
                            territoryMemberId = it.territoryMemberReport.territoryMemberId
                        )
                    )
                }
            }

            else -> territoriesRepository.getTerritoryStreet(request.territoryStreetId)
                .map {
                    Response(
                        TerritoryMemberReport(
                            territoryStreet = it,
                            territoryMemberId = UUID.randomUUID()
                        )
                    )
                }
        }

        else -> territoryReportsRepository.get(request.territoryReportId).map { Response(it) }
    }

    data class Request(
        val territoryReportId: UUID? = null,
        val territoryStreetId: UUID? = null,
        val houseId: UUID? = null,
        val roomId: UUID? = null
    ) : UseCase.Request

    data class Response(val territoryMemberReport: TerritoryMemberReport) : UseCase.Response
}