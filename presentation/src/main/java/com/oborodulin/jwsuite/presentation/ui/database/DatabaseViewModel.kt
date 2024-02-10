package com.oborodulin.jwsuite.presentation.ui.database

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.model.DatabaseUiModel
import kotlinx.coroutines.flow.StateFlow

interface DatabaseViewModel :
    DialogViewModeled<DatabaseUiModel, DatabaseUiAction, UiSingleEvent, DatabaseFields> {
    val databaseBackupPeriod: StateFlow<InputWrapper>
}