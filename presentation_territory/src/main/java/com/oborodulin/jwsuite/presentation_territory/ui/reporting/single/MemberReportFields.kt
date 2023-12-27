package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class MemberReportFields : Focusable {
    MEMBER_REPORT_ID,
    MEMBER_REPORT_HOUSE,
    MEMBER_REPORT_ROOM,
    MEMBER_REPORT_MARK,
    MEMBER_REPORT_GENDER,
    MEMBER_REPORT_AGE,
    MEMBER_REPORT_DESC;

    override fun key(): String {
        return this.name
    }
}
