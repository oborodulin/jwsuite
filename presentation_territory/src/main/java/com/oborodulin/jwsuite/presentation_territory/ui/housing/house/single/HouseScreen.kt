package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.HouseInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Territoring.HouseScreen"

@Composable
fun HouseScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: HouseViewModelImpl = hiltViewModel(),
    houseInput: HouseInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("HouseScreen(...) called: houseInput = %s", houseInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Housing.route) }
    val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
    val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
    appState.handleTopBarNavClick.value =
        { if (isUiStateChanged) isCancelChangesShowAlert.value = true else upNavigation() }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = houseInput?.houseId,
        loadUiAction = HouseUiAction.Load(houseInput?.houseId),
        saveUiAction = HouseUiAction.Save,
        upNavigation = upNavigation,
        isCancelChangesShowAlert = isCancelChangesShowAlert,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_house,
        uniqueConstraintFailedResId = R.string.house_unique_constraint_error,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { HouseView(sharedViewModel = appState.sharedViewModel.value) }
}