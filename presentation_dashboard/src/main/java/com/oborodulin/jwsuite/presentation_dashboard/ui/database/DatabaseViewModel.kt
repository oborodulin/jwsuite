package com.oborodulin.jwsuite.presentation_dashboard.ui.database

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel
import kotlinx.coroutines.flow.StateFlow

interface DatabaseViewModel :
    DialogViewModeled<DatabaseUiModel, DatabaseUiAction, UiSingleEvent, DatabaseFields> {
    val receiverMember: StateFlow<InputListItemWrapper<ListItemModel>>
}