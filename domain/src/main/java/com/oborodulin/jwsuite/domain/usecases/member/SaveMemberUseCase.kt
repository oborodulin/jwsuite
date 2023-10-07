package com.oborodulin.jwsuite.domain.usecases.member

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveMemberUseCase(
    configuration: Configuration,
    private val membersRepository: MembersRepository
) : UseCase<SaveMemberUseCase.Request, SaveMemberUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return membersRepository.save(request.member)
            .map {
                Response(it)
            }.catch { throw UseCaseException.MemberSaveException(it) }
    }

    data class Request(val member: Member) : UseCase.Request
    data class Response(val member: Member) : UseCase.Response
}
