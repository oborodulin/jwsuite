package com.oborodulin.jwsuite.domain.usecases.georegiondistrict

data class RegionDistrictUseCases(
    val getRegionDistrictsUseCase: GetRegionDistrictsUseCase,
    val getRegionDistrictUseCase: GetRegionDistrictUseCase,
    val saveRegionDistrictUseCase: SaveRegionDistrictUseCase,
    val deleteRegionDistrictUseCase: DeleteRegionDistrictUseCase
)