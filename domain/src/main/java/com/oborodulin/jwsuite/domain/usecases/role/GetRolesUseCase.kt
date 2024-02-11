package com.oborodulin.jwsuite.domain.usecases.role

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.RolesRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRolesUseCase(
    configuration: Configuration, private val rolesRepository: RolesRepository,
    private val membersRepository: MembersRepository
) : UseCase<GetRolesUseCase.Request, GetRolesUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.memberId) {
        null -> rolesRepository.getAll()
        else -> membersRepository.getRolesForMember(request.memberId)
    }.map { Response(it) }

    data class Request(val memberId: UUID? = null) : UseCase.Request
    data class Response(val roles: List<Role>) : UseCase.Response
}