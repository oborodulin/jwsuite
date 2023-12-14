package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.handout

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.DialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.components.HandOutButtonComponent
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import timber.log.Timber

private const val TAG = "Territoring.HandOutConfirmationScreen"

@Composable
fun HandOutConfirmationScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoriesGridViewModel,//Impl = hiltViewModel()
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("HandOutConfirmationScreen(...) called")
    val appState = LocalAppState.current
    DialogScreenComponent(
        viewModel = viewModel,
        loadUiAction = TerritoriesGridUiAction.HandOutInitConfirmation,
        saveUiAction = TerritoriesGridUiAction.HandOut,
        upNavigation = {
            appState.barNavController.navigateUp(); appState.navigateToBarRoute(
            NavRoutes.Territoring.route
        )
        },
        handleTopBarNavClick = appState.handleTopBarNavClick,
        areInputsValid = viewModel.areHandOutInputsValid.collectAsStateWithLifecycle().value,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_hand_out,
        confirmButton = @Composable { areInputsValid, handleHandOutButtonClick ->
            HandOutButtonComponent(enabled = areInputsValid, onClick = handleHandOutButtonClick)
        },
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) {
        HandOutConfirmationView(
            sharedViewModel = appState.congregationSharedViewModel.value, viewModel = viewModel
        )
    }
    // Scaffold Hoisting:
    //onActionBarSubtitleChange(stringResource(dialogTitleResId))
}