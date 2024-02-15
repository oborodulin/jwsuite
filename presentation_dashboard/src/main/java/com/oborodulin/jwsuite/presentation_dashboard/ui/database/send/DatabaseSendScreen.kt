package com.oborodulin.jwsuite.presentation_dashboard.ui.database.send

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.DialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_dashboard.R
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.SendButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseUiAction
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseViewModelImpl
import timber.log.Timber

private const val TAG = "Presentation_Dashboard.DatabaseSendScreen"

@Composable
fun DatabaseSendScreen(
    viewModel: DatabaseViewModelImpl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("DatabaseSendScreen(...) called")
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        DialogScreenComponent(
            viewModel = viewModel,
            loadUiAction = DatabaseUiAction.Init,
            confirmUiAction = DatabaseUiAction.Send(),
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_database_send,
            confirmButton = @Composable { areInputsValid, handleSendButtonClick ->
                SendButtonComponent(
                    enabled = areInputsValid,
                    onClick = handleSendButtonClick
                )
            },
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { _, _, _, handleSendAction -> DatabaseSendView(handleSendAction = handleSendAction) }
    }
}