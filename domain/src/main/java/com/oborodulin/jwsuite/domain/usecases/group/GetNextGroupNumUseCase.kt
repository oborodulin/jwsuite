package com.oborodulin.jwsuite.domain.usecases.group

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextGroupNumUseCase(
    configuration: Configuration,
    private val groupsRepository: GroupsRepository
) : UseCase<GetNextGroupNumUseCase.Request, GetNextGroupNumUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        groupsRepository.getNextGroupNum(request.congregationId).map { Response(it) }

    data class Request(val congregationId: UUID) : UseCase.Request
    data class Response(val groupNum: Int) : UseCase.Response
}