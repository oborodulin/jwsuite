package com.oborodulin.jwsuite.domain.usecases.member

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteMemberUseCase(
    configuration: Configuration, private val membersRepository: MembersRepository
) : UseCase<DeleteMemberUseCase.Request, DeleteMemberUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return membersRepository.delete(request.memberId).map { Response }
    }

    data class Request(val memberId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
