package com.oborodulin.jwsuite.domain.usecases.geolocality

data class LocalityUseCases(
    val getLocalitiesUseCase: GetLocalitiesUseCase,
    val getAllLocalitiesUseCase: GetAllLocalitiesUseCase,
    val getLocalityUseCase: GetLocalityUseCase,
    val saveLocalityUseCase: SaveLocalityUseCase,
    val deleteLocalityUseCase: DeleteLocalityUseCase
)