package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.screen.DialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_dashboard.R
import timber.log.Timber

private const val TAG = "Dashboarding.DataManagementScreen"

@Composable
fun DataManagementScreen(
    viewModel: DataManagementViewModelImpl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("DataManagementScreen(...) called")
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitle = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_dashboard_settings),
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        DialogScreenComponent(
            viewModel = viewModel,
            loadUiAction = DataManagementUiAction.Load,
            confirmUiAction = DataManagementUiAction.Save,
            upNavigation = upNavigation,
            isNextActionPerform = false,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            isControlsShow = false,
            confirmResId = R.string.dlg_confirm_data_management,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_settings,
            confirmButton = { areValid, handleSaveButtonClick ->
                SaveButtonComponent(enabled = areValid, onClick = handleSaveButtonClick)
            },
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { uiModel, _, _, handleSaveAction ->
            DataManagementView(
                //modifier = Modifier.verticalScroll(rememberScrollState()),
                uiModel = uiModel,
                dataManagementViewModel = viewModel,
                handleSaveAction = handleSaveAction
            )
        }
    }
}