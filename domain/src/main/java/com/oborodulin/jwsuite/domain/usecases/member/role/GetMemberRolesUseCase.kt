package com.oborodulin.jwsuite.domain.usecases.member.role

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMemberRolesUseCase(
    configuration: Configuration, private val membersRepository: MembersRepository
) : UseCase<GetMemberRolesUseCase.Request, GetMemberRolesUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        membersRepository.getMemberRoles(request.memberId).map { Response(it) }

    data class Request(val memberId: UUID) : UseCase.Request
    data class Response(val memberRoles: List<MemberRole>) : UseCase.Response
}