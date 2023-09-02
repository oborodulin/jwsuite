package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class MemberInputEvent(val value: String) : Inputable {
    data class Congregation(val input: ListItemModel) : MemberInputEvent(input.headline)
    data class Group(val input: ListItemModel) : MemberInputEvent(input.headline)
    data class MemberNum(val input: String) : MemberInputEvent(input)
    data class MemberName(val input: String) : MemberInputEvent(input)
    data class Surname(val input: String) : MemberInputEvent(input)
    data class Patronymic(val input: String) : MemberInputEvent(input)
    data class Pseudonym(val input: String) : MemberInputEvent(input)
    data class PhoneNumber(val input: String) : MemberInputEvent(input)
    data class MemberType(val input: String) : MemberInputEvent(input)
    data class DateOfBirth(val input: String) : MemberInputEvent(input)
    data class DateOfBaptism(val input: String) : MemberInputEvent(input)
    data class InactiveDate(val input: String) : MemberInputEvent(input)

    override fun value(): String {
        return this.value
    }
}