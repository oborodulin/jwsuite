package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class TerritoryInputEvent(val value: String) : Inputable {
    data class Congregation(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class Group(val input: ListItemModel) : TerritoryInputEvent(input.headline)
    data class TerritoryNum(val input: String) : TerritoryInputEvent(input)
    data class TerritoryName(val input: String) : TerritoryInputEvent(input)
    data class Surname(val input: String) : TerritoryInputEvent(input)
    data class Patronymic(val input: String) : TerritoryInputEvent(input)
    data class Pseudonym(val input: String) : TerritoryInputEvent(input)
    data class PhoneNumber(val input: String) : TerritoryInputEvent(input)
    data class TerritoryType(val input: String) : TerritoryInputEvent(input)
    data class DateOfBirth(val input: String) : TerritoryInputEvent(input)
    data class DateOfBaptism(val input: String) : TerritoryInputEvent(input)
    data class InactiveDate(val input: String) : TerritoryInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
