package com.oborodulin.jwsuite.domain.usecases.member.role

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteMemberRoleUseCase(
    configuration: Configuration, private val membersRepository: MembersRepository
) : UseCase<DeleteMemberRoleUseCase.Request, DeleteMemberRoleUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return membersRepository.deleteMemberRole(request.memberRoleId)
            .map { Response }
    }

    data class Request(val memberRoleId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
