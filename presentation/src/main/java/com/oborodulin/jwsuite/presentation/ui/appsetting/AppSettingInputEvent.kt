package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem

sealed class AppSettingInputEvent(val value: String) : Inputable {
    data class Congregation(val input: CongregationsListItem) : AppSettingInputEvent(input.headline)
    data class Group(val input: ListItemModel) : AppSettingInputEvent(input.headline)
    data class AppSettingNum(val input: String) : AppSettingInputEvent(input)
    data class AppSettingName(val input: String) : AppSettingInputEvent(input)
    data class Surname(val input: String) : AppSettingInputEvent(input)
    data class Patronymic(val input: String) : AppSettingInputEvent(input)
    data class Pseudonym(val input: String) : AppSettingInputEvent(input)
    data class PhoneNumber(val input: String) : AppSettingInputEvent(input)
    data class DateOfBirth(val input: String) : AppSettingInputEvent(input)
    data class DateOfBaptism(val input: String) : AppSettingInputEvent(input)
    data class AppSettingType(val input: String) : AppSettingInputEvent(input)
    data class MovementDate(val input: String) : AppSettingInputEvent(input)
    data class LoginExpiredDate(val input: String) : AppSettingInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
