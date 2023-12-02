package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class MemberRoleFields : Focusable {
    MEMBER_ROLE_ID,
    MEMBER_ROLE_CONGREGATION,
    MEMBER_ROLE_MEMBER,
    MEMBER_ROLE_ROLE,
    MEMBER_ROLE_EXPIRED_DATE;

    override fun key(): String {
        return this.name
    }
}
