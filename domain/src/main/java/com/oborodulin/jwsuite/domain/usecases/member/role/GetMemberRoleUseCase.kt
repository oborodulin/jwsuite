package com.oborodulin.jwsuite.domain.usecases.member.role

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMemberRoleUseCase(
    configuration: Configuration, private val membersRepository: MembersRepository
) : UseCase<GetMemberRoleUseCase.Request, GetMemberRoleUseCase.Response>(configuration) {
    override fun process(request: Request) = membersRepository.getMemberRole(request.memberRoleId)
        .map { Response(it) }

    data class Request(val memberRoleId: UUID) : UseCase.Request
    data class Response(val memberRole: MemberRole) : UseCase.Response
}