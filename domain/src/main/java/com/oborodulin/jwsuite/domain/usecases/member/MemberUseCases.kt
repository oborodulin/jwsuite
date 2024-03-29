package com.oborodulin.jwsuite.domain.usecases.member

import com.oborodulin.jwsuite.domain.usecases.member.role.DeleteMemberRoleUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRoleUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRolesUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.SaveMemberRoleUseCase

data class MemberUseCases(
    val getMembersUseCase: GetMembersUseCase,
    val getMemberUseCase: GetMemberUseCase,
    val getMembersWithUsernameUseCase: GetMembersWithUsernameUseCase,
    val saveMemberUseCase: SaveMemberUseCase,
    val deleteMemberUseCase: DeleteMemberUseCase,
    val getMemberRolesUseCase: GetMemberRolesUseCase,
    val getMemberRoleUseCase: GetMemberRoleUseCase,
    val deleteMemberRoleUseCase: DeleteMemberRoleUseCase,
    val saveMemberRoleUseCase: SaveMemberRoleUseCase
)