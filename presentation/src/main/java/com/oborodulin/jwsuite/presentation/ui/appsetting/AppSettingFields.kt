package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class AppSettingFields : Focusable {
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
    MEMBER_LOGIN_EXPIRED_DATE,
    MEMBER_MOVEMENT_DATE;

    override fun key(): String {
        return this.name
    }
}