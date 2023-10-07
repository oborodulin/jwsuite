package com.oborodulin.jwsuite.domain.usecases.group

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetGroupUseCase(
    configuration: Configuration,
    private val groupsRepository: GroupsRepository
) : UseCase<GetGroupUseCase.Request, GetGroupUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        groupsRepository.get(request.groupId).map {
            Response(it)
        }

    data class Request(val groupId: UUID) : UseCase.Request
    data class Response(val group: Group) : UseCase.Response
}