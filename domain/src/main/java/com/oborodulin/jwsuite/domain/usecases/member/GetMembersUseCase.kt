package com.oborodulin.jwsuite.domain.usecases.member

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMembersUseCase(
    configuration: Configuration,
    private val membersRepository: MembersRepository
) : UseCase<GetMembersUseCase.Request, GetMembersUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = when (request.byCongregation) {
        true -> when (request.congregationId) {
            null -> membersRepository.getAllByFavoriteCongregation()
            else -> membersRepository.getAllByCongregation(request.congregationId)
        }

        false -> when (request.groupId) {
            null -> membersRepository.getAllByFavoriteCongregationGroup()
            else -> membersRepository.getAllByGroup(request.groupId)
        }
    }.map {
        Response(it)
    }

    data class Request(
        val congregationId: UUID? = null, val groupId: UUID? = null, val byCongregation: Boolean
    ) : UseCase.Request

    data class Response(val members: List<Member>) : UseCase.Response
}