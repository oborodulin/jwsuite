package com.oborodulin.jwsuite.domain.usecases.member.role

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveMemberRoleUseCase(
    configuration: Configuration, private val membersRepository: MembersRepository
) : UseCase<SaveMemberRoleUseCase.Request, SaveMemberRoleUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return membersRepository.saveMemberRole(request.memberRole)
            .map {
                Response(it)
            }.catch { throw UseCaseException.MemberRoleSaveException(it) }
    }

    data class Request(val memberRole: MemberRole) : UseCase.Request
    data class Response(val memberRole: MemberRole) : UseCase.Response
}
