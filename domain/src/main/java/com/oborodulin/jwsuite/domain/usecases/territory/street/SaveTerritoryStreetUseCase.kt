package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveTerritoryStreetUseCase(
    configuration: Configuration,
    private val territoryStreetsRepository: TerritoryStreetsRepository,
    private val housesRepository: HousesRepository
) : UseCase<SaveTerritoryStreetUseCase.Request, SaveTerritoryStreetUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return territoryStreetsRepository.saveTerritoryStreet(request.territoryStreet)
            .map { territoryStreet ->
                territoryStreet.id?.let {
                    if (territoryStreet.isCreateEstHouses) {
                        territoryStreet.isEvenSide?.let { isEven ->
                            if (isEven) (1..territoryStreet.estimatedHouses!!).filter { it % 2 == 0 }
                                .forEach { num ->
                                    housesRepository.save(
                                        House(
                                            street = territoryStreet.street,
                                            houseNum = num,
                                            isPrivateSector = territoryStreet.isPrivateSector
                                                ?: territoryStreet.street.isPrivateSector
                                        )
                                    )
                                } else (1..territoryStreet.estimatedHouses!!).filter { it % 2 != 0 }
                                .forEach { num ->
                                    housesRepository.save(
                                        House(
                                            street = territoryStreet.street,
                                            houseNum = num,
                                            isPrivateSector = territoryStreet.isPrivateSector
                                                ?: territoryStreet.street.isPrivateSector
                                        )
                                    )
                                }
                        } ?: (1..territoryStreet.estimatedHouses!!).forEach { num ->
                            housesRepository.save(
                                House(
                                    street = territoryStreet.street,
                                    houseNum = num,
                                    isPrivateSector = territoryStreet.isPrivateSector
                                        ?: territoryStreet.street.isPrivateSector
                                )
                            )
                        }
                    }
                }
                Response(territoryStreet)
            }.catch { throw UseCaseException.TerritoryStreetSaveException(it) }
    }

    data class Request(val territoryStreet: TerritoryStreet) : UseCase.Request
    data class Response(val territoryStreet: TerritoryStreet) : UseCase.Response
}
