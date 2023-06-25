package com.oborodulin.jwsuite.domain.usecases.georegiondistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteRegionUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<DeleteRegionUseCase.Request, DeleteRegionUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return congregationsRepository.deleteById(request.congregationId)
            .map {
                Response
            }
    }

    data class Request(val congregationId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
