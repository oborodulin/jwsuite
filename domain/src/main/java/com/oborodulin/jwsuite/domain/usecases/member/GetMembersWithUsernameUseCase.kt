package com.oborodulin.jwsuite.domain.usecases.member

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.MembersWithUsername
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetMembersWithUsernameUseCase(
    configuration: Configuration,
    private val membersRepository: MembersRepository,
    private val sessionManagerRepository: SessionManagerRepository
) : UseCase<GetMembersWithUsernameUseCase.Request, GetMembersWithUsernameUseCase.Response>(configuration) {
    override fun process(request: Request) = combine(
        sessionManagerRepository.username(), when (request.byCongregation) {
            true -> when (request.congregationId) {
                null -> membersRepository.getAllByFavoriteCongregation(request.isService)
                else -> membersRepository.getAllByCongregation(
                    request.congregationId, request.isService
                )
            }

            false -> when (request.groupId) {
                null -> membersRepository.getAllByFavoriteCongregationGroup(request.isService)
                else -> membersRepository.getAllByGroup(request.groupId, request.isService)
            }
        }
    ) { username, members -> Response(MembersWithUsername(username = username, members = members)) }

    data class Request(
        val congregationId: UUID? = null, val groupId: UUID? = null,
        val isService: Boolean = false, val byCongregation: Boolean
    ) : UseCase.Request

    data class Response(val membersWithUsername: MembersWithUsername) : UseCase.Response
}