package com.oborodulin.jwsuite.domain.usecases.member

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMemberUseCase(
    configuration: Configuration, private val membersRepository: MembersRepository
) : UseCase<GetMemberUseCase.Request, GetMemberUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        membersRepository.get(request.memberId).map {
            Response(it)
        }

    data class Request(val memberId: UUID) : UseCase.Request
    data class Response(val member: Member) : UseCase.Response
}