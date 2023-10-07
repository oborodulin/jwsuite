package com.oborodulin.jwsuite.domain.usecases.group

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveGroupUseCase(
    configuration: Configuration,
    private val groupsRepository: GroupsRepository
) : UseCase<SaveGroupUseCase.Request, SaveGroupUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return groupsRepository.save(request.group)
            .map {
                Response(it)
            }.catch { throw UseCaseException.GroupSaveException(it) }
    }

    data class Request(val group: Group) : UseCase.Request
    data class Response(val group: Group) : UseCase.Response
}
