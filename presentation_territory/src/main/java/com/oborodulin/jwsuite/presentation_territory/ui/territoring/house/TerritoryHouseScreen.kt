package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryHouseInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.TerritoryHouseScreen"

@Composable
fun TerritoryHouseScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryHouseViewModel: TerritoryHouseViewModelImpl = hiltViewModel(),
    territoryHouseInput: TerritoryHouseInput,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d("TerritoryHouseScreen(...) called: territoryHouseInput = %s", territoryHouseInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    val isUiStateChanged by territoryHouseViewModel.isUiStateChanged.collectAsStateWithLifecycle()
    val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
    appState.handleTopBarNavClick.value =
        { if (isUiStateChanged) isCancelChangesShowAlert.value = true else upNavigation() }
    SaveDialogScreenComponent(
        viewModel = territoryHouseViewModel,
        inputId = territoryHouseInput.territoryId,
        loadUiAction = TerritoryHouseUiAction.Load(territoryHouseInput.territoryId),
        saveUiAction = TerritoryHouseUiAction.Save,
        upNavigation = upNavigation,
        isCancelChangesShowAlert = isCancelChangesShowAlert,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_house,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) {
        TerritoryHouseView(
            territoryHousesUiModel = it,
            sharedViewModel = appState.sharedViewModel.value,
            territoryViewModel = territoryViewModel,
            territoryHouseViewModel = territoryHouseViewModel
        )
    }
}