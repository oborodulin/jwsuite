package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Reporting.MemberReportScreen"

@Composable
fun MemberReportScreen(
    viewModel: MemberReportViewModelImpl = hiltViewModel(),
    memberReportInput: NavigationInput.MemberReportInput,
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("MemberReportScreen(...) called: memberReportInput = %s", memberReportInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitleResId = NavRoutes.Territoring.titleResId,
        navRoute = NavRoutes.MemberReport,
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        SaveDialogScreenComponent(
            viewModel = viewModel,
            inputId = memberReportInput.territoryMemberReportId
                ?: memberReportInput.territoryStreetId ?: memberReportInput.houseId
                ?: memberReportInput.roomId,
            loadUiAction = MemberReportUiAction.Load(
                territoryMemberReportId = memberReportInput.territoryMemberReportId,
                territoryStreetId = memberReportInput.territoryStreetId,
                houseId = memberReportInput.houseId,
                roomId = memberReportInput.roomId
            ),
            saveUiAction = MemberReportUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_report,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { uiModel, _, _, handleSaveAction ->
            MemberReportView(
                uiModel = uiModel,
                sharedViewModel = appState.congregationSharedViewModel.value,
                handleSaveAction = handleSaveAction
            )
        }
    }
}