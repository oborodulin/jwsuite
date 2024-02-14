package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.screen.DialogScreenComponent
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Territoring.TerritorySettingScreen"

@Composable
fun TerritorySettingScreen(
    viewModel: TerritorySettingViewModelImpl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("TerritorySettingScreen(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritorySettingScreen -> LaunchedEffect(Unit)")
        viewModel.submitAction(TerritorySettingUiAction.Load)
    }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitle = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_territory_settings),
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        DialogScreenComponent(
            viewModel = viewModel,
            loadUiAction = TerritorySettingUiAction.Load,
            confirmUiAction = TerritorySettingUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            isControlsShow = session.containsRole(MemberRoleType.TERRITORIES),
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_settings,
            confirmButton = { areValid, handleSaveButtonClick ->
                SaveButtonComponent(enabled = areValid, onClick = handleSaveButtonClick)
            },
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { _, _, _, handleSaveAction ->
            TerritorySettingView(viewModel = viewModel, handleSaveAction = handleSaveAction)
        }
    }
}