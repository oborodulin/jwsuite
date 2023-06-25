package com.oborodulin.jwsuite.domain.usecases.georegiondistrict

data class RegionUseCases(
    val getRegionsUseCase: GetRegionsUseCase,
    val getRegionUseCase: GetRegionUseCase,
    val saveRegionUseCase: SaveRegionUseCase,
    val deleteRegionUseCase: DeleteRegionUseCase
)