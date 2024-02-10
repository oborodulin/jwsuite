package com.oborodulin.jwsuite.presentation.ui.database

import com.oborodulin.home.common.ui.state.UiAction

sealed class DatabaseUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Backup : DatabaseUiAction()
    data object Restore : DatabaseUiAction()
    data object Save : DatabaseUiAction()
}