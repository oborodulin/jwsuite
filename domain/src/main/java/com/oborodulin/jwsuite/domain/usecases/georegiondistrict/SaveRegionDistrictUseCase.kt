package com.oborodulin.jwsuite.domain.usecases.georegiondistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveRegionDistrictUseCase(
    configuration: Configuration,
    private val regionDistrictsRepository: GeoRegionDistrictsRepository
) : UseCase<SaveRegionDistrictUseCase.Request, SaveRegionDistrictUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return regionDistrictsRepository.save(request.regionDistrict)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GeoRegionDistrictSaveException(it) }
    }

    data class Request(val regionDistrict: GeoRegionDistrict) : UseCase.Request
    data class Response(val regionDistrict: GeoRegionDistrict) : UseCase.Response
}
