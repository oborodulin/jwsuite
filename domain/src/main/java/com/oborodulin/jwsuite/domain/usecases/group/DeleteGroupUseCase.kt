package com.oborodulin.jwsuite.domain.usecases.group

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteGroupUseCase(
    configuration: Configuration,
    private val groupsRepository: GroupsRepository
) : UseCase<DeleteGroupUseCase.Request, DeleteGroupUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return groupsRepository.deleteById(request.groupId)
            .map {
                Response
            }
    }

    data class Request(val groupId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
