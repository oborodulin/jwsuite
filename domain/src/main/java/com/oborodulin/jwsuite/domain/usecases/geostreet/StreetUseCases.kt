package com.oborodulin.jwsuite.domain.usecases.geostreet

data class StreetUseCases(
    val getStreetsUseCase: GetStreetsUseCase,
    val getStreetUseCase: GetStreetUseCase,
    val saveStreetUseCase: SaveStreetUseCase,
    val deleteStreetUseCase: DeleteStreetUseCase
)