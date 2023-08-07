package com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict

data class LocalityDistrictUseCases(
    val getLocalityDistrictsUseCase: GetLocalityDistrictsUseCase,
    val getLocalityDistrictUseCase: GetLocalityDistrictUseCase,
    val saveLocalityDistrictUseCase: SaveLocalityDistrictUseCase,
    val deleteLocalityDistrictUseCase: DeleteLocalityDistrictUseCase
)