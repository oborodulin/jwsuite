package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

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
    MEMBER_DATE_OF_BIRTH,
    MEMBER_DATE_OF_BAPTISM,
    MEMBER_CONGREGATION_ID,
    MEMBER_ACTIVITY_DATE,
    MEMBER_MOVEMENT_ID,
    MEMBER_TYPE,
    MEMBER_MOVEMENT_DATE,
    MEMBER_LOGIN_EXPIRED_DATE;

    override fun key() = this.name
}
