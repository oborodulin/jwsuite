package com.oborodulin.jwsuite.domain.usecases.group

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetGroupsUseCase(
    configuration: Configuration,
    private val groupsRepository: GroupsRepository
) : UseCase<GetGroupsUseCase.Request, GetGroupsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = when (request.congregationId) {
        null -> groupsRepository.getAllByFavoriteCongregation()
        else -> groupsRepository.getAllByCongregation(request.congregationId)
    }.map {
        Response(it)
    }

    data class Request(val congregationId: UUID? = null) : UseCase.Request
    data class Response(val groups: List<Group>) : UseCase.Response
}