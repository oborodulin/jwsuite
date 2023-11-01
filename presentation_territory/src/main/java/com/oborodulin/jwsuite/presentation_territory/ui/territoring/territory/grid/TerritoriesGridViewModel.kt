package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import androidx.compose.ui.text.input.TextFieldValue
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CheckedListDialogViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem
import kotlinx.coroutines.flow.StateFlow

interface TerritoriesGridViewModel :
    CheckedListDialogViewModeled<List<TerritoriesListItem>, TerritoriesGridUiAction, TerritoriesGridUiSingleEvent, TerritoriesFields, List<TerritoriesListItem>> {
    // search
    val handOutSearchText: StateFlow<TextFieldValue>
    val atWorkSearchText: StateFlow<TextFieldValue>
    val idleSearchText: StateFlow<TextFieldValue>
    fun onHandOutSearchTextChange(text: TextFieldValue)
    fun onAtWorkSearchTextChange(text: TextFieldValue)
    fun onIdleSearchTextChange(text: TextFieldValue)

    val member: StateFlow<InputListItemWrapper<ListItemModel>>
    val receivingDate: StateFlow<InputWrapper>
    val deliveryDate: StateFlow<InputWrapper>

    val areHandOutInputsValid: StateFlow<Boolean>
    val areAtWorkProcessInputsValid: StateFlow<Boolean>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}