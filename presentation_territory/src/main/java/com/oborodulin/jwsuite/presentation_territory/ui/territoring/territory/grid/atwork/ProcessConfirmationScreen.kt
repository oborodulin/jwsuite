package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.atwork

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import timber.log.Timber

private const val TAG = "Territoring.ProcessConfirmationScreen"

@Composable
fun ProcessConfirmationScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoriesGridViewModel,//Impl = hiltViewModel()
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("ProcessConfirmationScreen(...) called")
    val appState = LocalAppState.current
    SaveDialogScreenComponent(
        viewModel = viewModel,
        loadUiAction = TerritoriesGridUiAction.ProcessInitConfirmation,
        saveUiAction = TerritoriesGridUiAction.Process,
        upNavigation = { appState.backToBottomBarScreen() },
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_process,
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) {
        ProcessConfirmationView(
            sharedViewModel = appState.sharedViewModel.value, viewModel = viewModel
        )
    }
}