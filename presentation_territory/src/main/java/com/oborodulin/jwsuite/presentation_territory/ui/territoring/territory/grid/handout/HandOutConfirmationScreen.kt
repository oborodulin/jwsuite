package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.handout

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.DialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
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
    viewModel: TerritoriesGridViewModel,//Impl = hiltViewModel()
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("HandOutConfirmationScreen(...) called")
    val appState = LocalAppState.current
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitleResId = NavRoutes.Territoring.titleResId,
        navRoute = NavRoutes.HandOutConfirmation,
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        DialogScreenComponent(
            viewModel = viewModel,
            loadUiAction = TerritoriesGridUiAction.HandOutInitConfirmation,
            confirmUiAction = TerritoriesGridUiAction.HandOut,
            upNavigation = {
                appState.barNavController.navigateUp()
                appState.navigateToBarRoute(NavRoutes.Territoring.route)
            },
            handleTopBarNavClick = appState.handleTopBarNavClick,
            areInputsValid = viewModel.areHandOutInputsValid.collectAsStateWithLifecycle().value,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_hand_out,
            confirmButton = @Composable { areInputsValid, handleHandOutButtonClick ->
                HandOutButtonComponent(
                    enabled = areInputsValid,
                    onClick = handleHandOutButtonClick
                )
            },
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { _, _, _, _ ->
            HandOutConfirmationView(
                sharedViewModel = appState.congregationSharedViewModel.value,
                viewModel = viewModel
            )
        }
    }
}