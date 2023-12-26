package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.memberreport.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem

sealed class MemberReportInputEvent(val value: String) : Inputable {
    data class Congregation(val input: CongregationsListItem) : MemberReportInputEvent(input.headline)
    data class Group(val input: ListItemModel) : MemberReportInputEvent(input.headline)
    data class MemberReportNum(val input: String) : MemberReportInputEvent(input)
    data class MemberReportName(val input: String) : MemberReportInputEvent(input)
    data class Surname(val input: String) : MemberReportInputEvent(input)
    data class Patronymic(val input: String) : MemberReportInputEvent(input)
    data class Pseudonym(val input: String) : MemberReportInputEvent(input)
    data class PhoneNumber(val input: String) : MemberReportInputEvent(input)
    data class DateOfBirth(val input: String) : MemberReportInputEvent(input)
    data class DateOfBaptism(val input: String) : MemberReportInputEvent(input)
    data class MemberReportType(val input: String) : MemberReportInputEvent(input)
    data class MovementDate(val input: String) : MemberReportInputEvent(input)
    data class LoginExpiredDate(val input: String) : MemberReportInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
