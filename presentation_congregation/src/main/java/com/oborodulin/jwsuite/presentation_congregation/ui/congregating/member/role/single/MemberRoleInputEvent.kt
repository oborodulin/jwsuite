package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class MemberRoleInputEvent(val value: String) : Inputable {
    data class Congregation(val input: ListItemModel) : MemberRoleInputEvent(input.headline)
    data class Member(val input: ListItemModel) : MemberRoleInputEvent(input.headline)
    data class Role(val input: ListItemModel) : MemberRoleInputEvent(input.headline)
    data class RoleExpiredDate(val input: String) : MemberRoleInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
