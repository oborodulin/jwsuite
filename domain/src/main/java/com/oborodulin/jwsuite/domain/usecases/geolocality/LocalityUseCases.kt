package com.oborodulin.jwsuite.domain.usecases.geolocality

data class LocalityUseCases(
    val getLocalitiesUseCase: GetLocalitiesUseCase,
    val getLocalityUseCase: GetLocalityUseCase,
    val saveLocalityUseCase: SaveLocalityUseCase,
    val deleteLocalityUseCase: DeleteLocalityUseCase
)