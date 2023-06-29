package com.oborodulin.jwsuite.domain.usecases.member

data class MemberUseCases(
    val getMembersUseCase: GetMembersUseCase,
    val getMemberUseCase: GetMemberUseCase,
    val saveMemberUseCase: SaveMemberUseCase,
    val deleteMemberUseCase: DeleteMemberUseCase
)