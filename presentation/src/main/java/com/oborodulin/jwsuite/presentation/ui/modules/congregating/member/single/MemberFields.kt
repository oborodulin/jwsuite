package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class MemberFields : Focusable {
    MEMBER_ID,
    MEMBER_CONGREGATION,
    MEMBER_GROUP,
    MEMBER_NUM,
    MEMBER_NAME,
    MEMBER_SURNAME,
    MEMBER_PATRONYMIC,
    MEMBER_PSEUDONYM,
    MEMBER_PHONE_NUMBER,
    MEMBER_TYPE,
    MEMBER_DATE_OF_BIRTH,
    MEMBER_DATE_OF_BAPTISM,
    MEMBER_INACTIVE_DATE;

    override fun key(): String {
        return this.name
    }
}
