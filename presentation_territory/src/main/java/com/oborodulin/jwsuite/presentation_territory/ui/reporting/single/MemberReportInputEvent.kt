package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class MemberReportInputEvent(val value: String) : Inputable {
    data class House(val input: ListItemModel) : MemberReportInputEvent(input.headline)
    data class Room(val input: ListItemModel) : MemberReportInputEvent(input.headline)
    data class ReportMark(val input: String) : MemberReportInputEvent(input)
    data class Gender(val input: String) : MemberReportInputEvent(input)
    data class Age(val input: String) : MemberReportInputEvent(input)
    data class Desc(val input: String) : MemberReportInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
