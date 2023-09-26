package com.oborodulin.jwsuite.domain.usecases.geostreet

data class StreetUseCases(
    val getStreetsUseCase: GetStreetsUseCase,
    val getStreetUseCase: GetStreetUseCase,
    val saveStreetUseCase: SaveStreetUseCase,
    val deleteStreetUseCase: DeleteStreetUseCase,
    val deleteStreetLocalityDistrictUseCase:DeleteStreetLocalityDistrictUseCase,
    val deleteStreetMicrodistrictUseCase: DeleteStreetMicrodistrictUseCase,
    val getStreetsForTerritoryUseCase: GetStreetsForTerritoryUseCase,
    val getLocalityDistrictsForStreetUseCase:GetLocalityDistrictsForStreetUseCase,
    val getMicrodistrictsForStreetUseCase: GetMicrodistrictsForStreetUseCase,
    val saveStreetLocalityDistrictsUseCase: SaveStreetLocalityDistrictsUseCase,
    val saveStreetMicrodistrictsUseCase: SaveStreetMicrodistrictsUseCase
)